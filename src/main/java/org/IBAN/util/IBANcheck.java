package org.IBAN.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
public class IBANcheck {
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
	String ibanS;
	String countryCode;
	String checkSum;
    String blz;
	String ktnr;
	int check;
	private static final Integer[] WEIGHT_00 = new Integer[] {2, 1, 2, 1, 2, 1, 2, 1, 2};
	private static final Integer[] WEIGHT_01 = new Integer[] {3, 7, 1, 3, 7, 1, 3, 7, 1 };
	private static final Integer[] WEIGHT_02 = new Integer[] {2, 3, 4, 5, 6, 7, 8, 9, 2 };
	private static final Integer[] WEIGHT_03 = new Integer[] {2, 3, 4, 5, 6, 7, 2, 3, 4};
	private static final Integer[] WEIGHT_04 = new Integer[] {7, 3, 1, 7, 3, 1, 7, 3, 1 };
	private static final Integer[] WEIGHT_05 = new Integer[] {2,  3,  4,  5,  6,  7,  2,  3};
	private static final Integer[] WEIGHT_06 = new Integer[] { 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	public void createSubpartsGermanIBAN(String ibanS) {
		if(ibanS.length() == 22) {
			final String cc = String.join("|", countryCodes);
			final Pattern    IBANP    = Pattern.compile("^(" + cc + ")(\\d{2})(\\d{8})(\\d{10})$");
			final Matcher matcher = IBANP.matcher(ibanS);
			assert(matcher.matches());
			matcher.matches();
			this.countryCode = matcher.group(1);
			if(this.countryCode.equals("DE")) {
				this.checkSum = matcher.group(2);
				this.blz    = matcher.group(3);
				this.ktnr   = matcher.group(4);
				this.check   = digit(ktnr.charAt(ktnr.length()-1));
				System.out.println(countryCode + " " + checkSum + " " + blz + " " + ktnr);
				testMethod10();
			}
        }
	}
	private void testMethod10() {
		final List<Integer> digits  = this.ktnr.chars().map(IBANcheck::digit).boxed().collect(Collectors.<Integer>toList());
		final List<Integer> factors = reverse(Arrays.asList(WEIGHT_06));
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
