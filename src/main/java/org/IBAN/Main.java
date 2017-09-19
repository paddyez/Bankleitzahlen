package org.IBAN;
import  org.IBAN.util.IBANcheck;
public final class Main {
    public static void main(String[] args) {
		if(args != null && args.length > 0) {
			String ibanS = "";
			for(String arg: args) {
				ibanS += arg.replaceAll("\\s+","");
			}
			IBANcheck ibanC = new IBANcheck();
			ibanC.createSubpartsGermanIBAN(ibanS);
		}
	}
}
