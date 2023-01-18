package net.coderodde.cskit.sorting;

import java.util.Comparator;
import static net.coderodde.cskit.Utilities.debugPrintArray;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests <code>RunScanner</code>.
 *
 * @author Rodion Efremov
 */
public class RunScannerTest {

    private static final class AscendingComparator
    implements Comparator<Integer> {

        @Override
        public int compare(Integer i1, Integer i2) {
            return i1 - i2;
        }
    