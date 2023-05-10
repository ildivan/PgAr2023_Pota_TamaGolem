package pota;

import java.io.PrintStream;

import static java.lang.String.format;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The <strong>PrettyPrinter</strong> class creates and prints a table starting from a given matrix of Strings.
 * It creates borders and cells adapting them to the single value length.
 * This class is also compatible with ANSI codes written inside the matrix's values.
 */
public final class PrettyPrinter {

    private static final char BORDER_KNOT = '+';
    private static final char HORIZONTAL_BORDER = '-';
    private static final char VERTICAL_BORDER = '|';

    private static final String DEFAULT_AS_NULL = "(NULL)";

    /**
     * The PrintStream needed to print the table.
     */
    private final PrintStream out;

    /**
     * The default value to print if a table's value is null.
     */
    private final String asNull;

    /**
     * Constructor that creates a <code>PrettyPrinter</code> simple object.
     * It requires a PrintStream to be passed. 
     * Then it calls the contructor {@link #PrettyPrinter(PrintStream, String)}
     * with the PrintStream given and the default string for null values "(NULL)".
     * @param out The PrintStream needed to print the table.
     */
    public PrettyPrinter(PrintStream out) {
        this(out, DEFAULT_AS_NULL);
    }

    /**
     * Constructor that creates a <code>PrettyPrinter</code> object specifying 
     * the PrintStream and the default value to print for null values.
     * If the PrintStream or the default String is null, an error is thrown.
     * @param out The PrintStream needed to print the table.
     * @param asNull The default value to print if a table's value is null.
     */
    public PrettyPrinter(PrintStream out, String asNull) {
        if ( out == null ) {
            throw new IllegalArgumentException("No print stream provided");
        }
        if ( asNull == null ) {
            throw new IllegalArgumentException("No NULL-value placeholder provided");
        }
        this.out = out;
        this.asNull = asNull;
    }

    /**
     * Main PrettyPrinter method. It calculates the length of the table's values and
     * it generates a custom border to enclose values.
     * If the table is null, an error is thrown.
     * @param table The matrix of Strings to print.
     */
    public void print(String[][] table) {
        if ( table == null ) {
            throw new IllegalArgumentException("No tabular data provided");
        }
        if ( table.length == 0 ) {
            return;
        }
        final int[] widths = new int[getMaxColumns(table)];
        adjustColumnWidths(table, widths);
        printPreparedTable(table, widths, getHorizontalBorder(widths));
    }

    // Prints each row of the built table.
    private void printPreparedTable(String[][] table, int[] widths, String horizontalBorder) {
        final int lineLength = horizontalBorder.length();
        out.println(horizontalBorder);
        for ( final String[] row : table ) {
            if ( row != null ) {
                out.println(getRow(row, widths, lineLength));
                out.println(horizontalBorder);
            }
        }
    }

    // Returns a row containing the values of the matrix's cells and the formatting of the table.
    private String getRow(String[] row, int[] widths, int lineLength) {
        final StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL_BORDER);
        final int maxWidths = widths.length;
        for ( int i = 0; i < maxWidths; i++ ) {
            builder.append(padRight(getCellValue(safeGet(row, i, null)), widths[i])).append(VERTICAL_BORDER);
        }
        return builder.toString();
    }

    // Calculates and return the border that separates the cells of the table.
    private String getHorizontalBorder(int[] widths) {
        final StringBuilder builder = new StringBuilder(256);
        builder.append(BORDER_KNOT);
        for ( final int w : widths ) {
            for ( int i = 0; i < w; i++ ) {
                builder.append(HORIZONTAL_BORDER);
            }
            builder.append(BORDER_KNOT);
        }
        return builder.toString();
    }

    // Returns the total columns of the matrix.
    private int getMaxColumns(String[][] rows) {
        int max = 0;
        for ( final String[] row : rows ) {
            if ( row != null && row.length > max ) {
                max = row.length;
            }
        }
        return max;
    }

    // Calculates column widths and stores them in an array of int.
    private void adjustColumnWidths(String[][] rows, int[] widths) {
        for ( final String[] row : rows ) {
            if ( row != null ) {
                for ( int c = 0; c < widths.length; c++ ) {
                    final String cv = patternMatch(getCellValue(safeGet(row, c, asNull)));
                    final int l = cv.length();
                    if ( widths[c] < l ) {
                        widths[c] = l;
                    }
                }
            }
        }
    }

    /*
     * Calculates the total spaces to append to the single cell, 
     * so that all cells have the same length.
     */
    private static String padRight(String s, int n) {
        String fixPattern = patternMatch(s);
        int totalLength = n - fixPattern.length() + s.length();
        return format("%" + totalLength + "s", s);
    }

    // Returns the object stored in a single cell of the matrix.
    private static String safeGet(String[] array, int index, String defaultValue) {
        return index < array.length ? array[index] : defaultValue;
    }

    // Returns the .toString() value of the method argument.
    private String getCellValue(Object value) {
        return value == null ? asNull : value.toString();
    }

    /*
     * A Regex detects if the string contains a word in uppercase.
     * If detected, returns it, otherwise return the argument string.
     */
    private static String patternMatch(String str) {
        Matcher matcher = Pattern.compile("[A-Z]+").matcher(str);
        return matcher.find() ? matcher.group() : str;
    }

}