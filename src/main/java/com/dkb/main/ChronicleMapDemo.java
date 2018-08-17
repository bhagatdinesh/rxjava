package com.dkb.main;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.values.Values;

import java.io.File;
import java.io.IOException;

public class ChronicleMapDemo {
    public static void main(String[] args) throws IOException {
        File cityPostalCodesFile = new File("/<path>/demo/rxjava-demo/src/main/resource/a.txt");
//        ChronicleMap<CharSequence, PostalCodeRange> cityPostalCodes = createCacheFile(cityPostalCodesFile);
        ChronicleMap<CharSequence, PostalCodeRange> cityPostalCodes = recoverCache(cityPostalCodesFile);

        readCache(cityPostalCodes);
        cityPostalCodes.close();
    }

    private static ChronicleMap<CharSequence, PostalCodeRange> recoverCache(File cityPostalCodesFile) throws IOException {
        // read disk file and create cache
        return ChronicleMap
                .of(CharSequence.class, PostalCodeRange.class)
                .name("city-postal-codes-map")
                .averageKey("Amsterdam")
                .entries(50_000)
                .createOrRecoverPersistedTo(cityPostalCodesFile, false);
    }

    private static ChronicleMap<CharSequence, PostalCodeRange> createCacheFile(final File cityPostalCodesFile) throws IOException {
        // create cache
        ChronicleMap<CharSequence, PostalCodeRange> cityPostalCodes = ChronicleMap
                        .of(CharSequence.class, PostalCodeRange.class)
                        .name("city-postal-codes-map")
                        .averageKey("abcde")
                        .entries(1)
                        .createPersistedTo(cityPostalCodesFile);
        PostalCodeRange amsterdamCodes = Values.newHeapInstance(PostalCodeRange.class);
        amsterdamCodes.minCode(1011);
        amsterdamCodes.maxCode(1183);
        cityPostalCodes.put("Amsterdam", amsterdamCodes);

        PostalCodeRange amsterdamCodes1 = Values.newHeapInstance(PostalCodeRange.class);
        amsterdamCodes1.minCode(2011);
        amsterdamCodes1.maxCode(2183);
        cityPostalCodes.putIfAbsent("Amsterdam1", amsterdamCodes1);
        return cityPostalCodes;
    }

    private static void readCache(ChronicleMap<CharSequence, PostalCodeRange> cityPostalCodes) {
        System.out.println(cityPostalCodes.toString());
    }
}
