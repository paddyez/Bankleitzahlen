import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
public final class Main {
    private static final Integer[]  FACTORS = new Integer[] { 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	private static final HashSet<String> countryCodes = new HashSet<>(Arrays.asList("AC", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AN", "AO", "AQ", "AR", "AS", "AT", "AU", "AW", "AX", "AZ",
		"BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BL", "BM", "BN", "BO", "BQ", "BR", "BS", "BT", "BU", "BV", "BW", "BY", "BZ",
		"CA", "CC", "CD", "CE", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CP", "CR", "CS", "CU", "CV", "CW", "CX", "CY", "CZ",
		"DE", "DD", "DG", "DJ", "DK", "DM", "DO", "DZ", "EA", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "EU", "FI", "FJ", "FK", "FM", "FO", "FR", "FX",
		"GA", "GB", "GD", "GE", "GF", "GG", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GS", "GT", "GU", "GW", "GY",
		"HK", "HM", "HN", "HR", "HT", "HU", "IC", "ID", "IE", "IL", "IM", "IN", "IO", "IQ", "IR", "IS", "IT", "JE", "JM", "JO", "JP",
		"KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY",
		"MA", "MC", "MD", "ME", "MF", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ",
		"NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NT", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH",
		"PK", "PL", "PM", "PN", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RS", "RU", "RW",
		"SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "SS", "ST", "SU", "SV", "SX", "SY", "SZ",
		"TC", "TD", "TF", "TG",	"TA", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ",
		"UA", "UG", "UM", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "XK", "XS", "YE", "YT", "YU", "ZA", "ZM", "ZR", "ZW"));
    public static void main(String[] args) {
        test(args[0]);
    }
    public static void test(String iban) {
		final String cc = String.join("|", countryCodes);
		final Pattern    IBAN    = Pattern.compile("^(" + cc + ")(\\d{2})(\\d{8})(\\d{10})$");
        final Matcher matcher = IBAN.matcher(iban);
		matcher.matches();
        assert(matcher.matches());
		final String countryCode = matcher.group(1);
		final String chceckSum = matcher.group(2);
        final String blz    = matcher.group(3);
        final String ktnr   = matcher.group(4);
        final int   check   = digit(ktnr.charAt(ktnr.length()-1));
        System.out.println(countryCode + " " + chceckSum + " " + blz + " " + ktnr);
        final List<Integer> digits  = ktnr.chars().map(Main::digit).boxed().collect(Collectors.<Integer>toList());
        final List<Integer> factors = reverse(Arrays.asList(FACTORS));
        final int           sum     = zipWith(digits, factors, (a,b) -> a * b).stream().mapToInt(i -> i).sum();
        final int       result1 = (11 - (sum % 11)) % 11;
        final int       result  = result1 == 10 ? 0 : result1;
        final boolean   valid   = result == check;
        assert valid;
        System.out.println(result);
    }
    private static int digit(int c) {
        return c - '0';
    }
    private static <T> List<T> reverse(List<T> values) {
        final List<T>   tmp = new ArrayList<>(values);
        Collections.reverse(tmp);
        return Collections.unmodifiableList(tmp);
    }
    private static <A,B,C> List<C> zipWith(List<A> as, List<B> bs, BiFunction<A,B,C> with) {
        final int       size = Math.min(as.size(), bs.size());
        final List<C>   tmp = new ArrayList<>(size);
        for (int i=0; i<size; i++)  tmp.add(with.apply(as.get(i), bs.get(i)));
        return Collections.unmodifiableList(tmp);
    }
}
