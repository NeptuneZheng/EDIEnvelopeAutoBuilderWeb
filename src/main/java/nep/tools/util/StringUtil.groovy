package nep.tools.util

import org.apache.commons.codec.digest.DigestUtils

class StringUtil {

    public static BigInteger[] StringArrayMD5ToBigInteger(String[] parms){
        BigInteger[] result = new BigInteger[parms.size()]
        for (int i = 0; i < parms.size(); i++){
            result[i] = StringMD5ToBigInteger(parms[i])
        }
        return result
    }
    public static BigInteger StringMD5ToBigInteger(String md5){
        if(md5 == null){
            md5 = ''
        }
        return new BigInteger(DigestUtils.md5Hex(md5),16)
    }
}
