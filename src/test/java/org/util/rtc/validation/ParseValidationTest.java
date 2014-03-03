package org.util.rtc.validation;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;


public class ParseValidationTest {
    @Test
    public void testMakeValidationsFromPackages() throws Exception {
        ParseValidation parseValidation = new ParseValidation();
        //ArrayList<Package> packages = new ArrayList<Package>();
       // packages.add(Package.getPackage("java.util.Locale"));
        //parseValidation.setPackageList(packages);
        parseValidation.makeValidationsFromPackages(Locale.getDefault());
    }
}
