import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.List;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;
class Methode10 {
	public static void main(String[] args) {
		//String IBAN = "DE51600901000464321000";
		String IBAN = args[0];
		String BLZ = IBAN.substring(4,12);
		String Kontonummer = IBAN.substring(12);
		int pruefziffer = Integer.valueOf(Kontonummer.substring(9));
		System.out.println(BLZ + " " + Kontonummer);
		int multply[] = {2, 3, 4, 5, 6, 7, 8, 9, 10};
		IntStream integerS = IntStream.range(0, Kontonummer.length() -1).map(i -> Integer.valueOf(Kontonummer.substring(i, i+1)));
		Stream<Integer> boxedS = integerS.boxed();
		List<Integer> digitsL = boxedS.collect(Collectors.toList());
		Collections.reverse(digitsL);
		int sum = 0;
		int product = 0;
		int result;
		int i = 0;
		while(i < digitsL.size()) {
			product = digitsL.get(i) * multply[i];
			sum += product;
			System.out.println(product);
			i++;
		}
		System.out.println("--");
		result = (11 - (sum % 11)) % 11;
		result = result == 10 ? 0 : result;
		assert result == pruefziffer;
		System.out.println(result);
	}
}
