package studio.secretingredients.consult4me.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;

@Slf4j
public class Util {

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            log.info(pair.getKey() + " = " + pair.getValue());
        }
    }
}
