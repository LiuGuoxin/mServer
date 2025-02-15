package handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import tools.HexTool;

public class ExternalCodeTableGetter
{
    final Properties props;
    
    public ExternalCodeTableGetter(final Properties properties) {
        this.props = properties;
    }
    
    private static final <T extends Enum> T valueOf(final String name, final T[] values) {
        for (final T val : values) {
            if (((java.lang.Enum)val).name().equals(name)) {
                return val;
            }
        }
        return null;
    }
    
    private final <T extends java.lang.Enum> short getValue(final String name, final T[] values, final short def) {
        final String prop = this.props.getProperty(name);
        if (prop == null || prop.length() <= 0) {
            return def;
        }
        final String trimmed = prop.trim();
        final String[] args = trimmed.split(" ");
        int base = 0;
        String offset;
        if (args.length == 2) {
            base = ((WritableIntValueHolder)valueOf(args[0], values)).getValue();
            if (base == def) {
                base = this.getValue(args[0], (Enum[])values, def);
            }
            offset = args[1];
        }
        else {
            offset = args[0];
        }
        if (offset.length() > 2 && offset.substring(0, 2).equals("0x")) {
            return (short)(Short.parseShort(offset.substring(2), 16) + base);
        }
        return (short)(Short.parseShort(offset) + base);
    }
    
    public static final <T extends java.lang.Enum> String getOpcodeTable(final T[] enumeration) {
        final StringBuilder enumVals = new StringBuilder();
        final List<T> all = new ArrayList<T>();
        all.addAll((Collection<? extends T>)Arrays.asList(enumeration));
        Collections.sort(all, (Comparator<T>)new Comparator<WritableIntValueHolder>() {
            @Override
            public int compare(final WritableIntValueHolder o1, final WritableIntValueHolder o2) {
                return Short.valueOf(o1.getValue()).compareTo(o2.getValue());
            }
        });
        for (final T code : all) {
            enumVals.append(((java.lang.Enum)code).name());
            enumVals.append(" = ");
            enumVals.append("0x");
            enumVals.append(HexTool.toString(((WritableIntValueHolder)code).getValue()));
            enumVals.append(" (");
            enumVals.append(((WritableIntValueHolder)code).getValue());
            enumVals.append(")\n");
        }
        return enumVals.toString();
    }
    
    public static final <T extends java.lang.Enum> void populateValues(final Properties properties, final T[] values) {
        final ExternalCodeTableGetter exc = new ExternalCodeTableGetter(properties);
        for (final T code : values) {
            ((WritableIntValueHolder)code).setValue(exc.getValue(((java.lang.Enum)code).name(), values, (short)(-2)));
        }
    }
}
