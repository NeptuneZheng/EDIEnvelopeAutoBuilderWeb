package b2b.tools.cosco

import java.util.concurrent.Callable
import java.util.regex.Matcher
import java.util.regex.Pattern

class CallableFilePair implements Callable<Map<String,List<String>>> {
    private String filePath, rgex
    //linked booking number
    public static final String[] BKG_NO = ['4518152040','4518536500','4518539790','4518539880','4518540260','4518540270',
                                     '4518540280','4520619240','4520619250','4520619260','4520619650','4520619690',
                                     '4520741950','4520749260','4520749270','4520749280','4520750160','4520753820',
                                     '4520756020','4520756030','4520802740','5124817210','5124817220','6063164410',
                                     '6063164490','6063164500','6063164510','6063164520','6063164530','6063164540',
                                     '6063164550','6063164560','6063164570','6063164580','6063164590','6063164600',
                                     '6063164610','6085264900','6151197210','6151197220','6151197620','6151197630',
                                     '6151197640','6151197780','6151197810','6151197820','6151197830','6151197840',
                                     '6151197940','6151197950','6151197960','6151197970','6151197990','6151198000',
                                     '6151198010','6151198020','6151198030','6151198040','6151198050','6151198060',
                                     '6151198070','6151198080','6151198090','6151198100','6151198110','6151198120',
                                     '6151198130','6151198140','6178963690','6178964080','6178964090','6178964100',
                                     '6178964110','6178964130','6178964150','6178964170','6178964370','6178964450',
                                     '6195186760','6195186770','6195190810','6195192930','6195192940','6195192950',
                                     '6195192960','6197044690','6199303890','6199303900','6199303910','6199303930',
                                     '6199303940','6199303950','6199303960','6199304120','6199304130','6199304140',
                                     '6199304500','6199304510','6199304520','6199304530','6199304540','6199304550',
                                     '6199304560','6199304570','6199304580','6199304590','6199304600','6199304610',
                                     '6199304620','6199414260','6199512560','6199514670','6201134140','6201134180',
                                     '6201134190','6201173800','6201209920','6201209930','6201216300','6201216310',
                                     '6201217970','6201222710','6201226040','6201226050','6201226060','6201226070',
                                     '6201226970','6201226980','6201227390','6201231200','6201232120','6201232130',
                                     '6201232440','6201232490','6201234140','6201234150','6201234160','6201236590',
                                     '6201354620','6202379110','6202426490','6203336540','6203361070','6203371700',
                                     '6203378000','6203378010','6203378020','6203476330','6203476340','6203480380',
                                     '6203480390','6203487920','6203487960','6203487970','6203487980','6203488570',
                                     '6203488580','6203488590','6203492660','6203492670','6203492680','6203497420',
                                     '6203510000','6203511090','6203517290','6203518210','6204265260','6204265270',
                                     '6204489020','6204501450','6204502050','9003348930','9003353410','9003354300',
                                     '9003354430','9003354560','9003354570','9003354830','9003354840','9004572350',
                                     '9004575280','9005413740','9005416680']

    CallableFilePair(String filePath, String rgex) {
        this.filePath = filePath
        this.rgex = rgex
    }

    CallableFilePair() {
    }

    public Map<String,List<String>> getBKGNoMap(String filePath, String rgex){
        Map<String,List<String>> map = new HashMap<>()
        File files = new File(filePath)
        BufferedReader bufferedReader
        files.listFiles().eachWithIndex{ curr_File, curr_File_idx ->
            bufferedReader = new BufferedReader(new FileReader(curr_File))
            String line = ""
            while((line = bufferedReader.readLine()) != null){
                String matchedStr = getMatchedStr(line,rgex)
                if(matchedStr){
                    List<String> list= map.get(matchedStr)
                    if(!list){
                        list = new ArrayList<>()
                    }
                    list.add(curr_File.getAbsolutePath())
                    map.put(matchedStr,list)
                    break
                }
            }
        }
        return map
    }

    String getMatchedStr(String source, String rgex) {
        Pattern pattern = Pattern.compile(rgex)
        Matcher m = pattern.matcher(source)

        while (m.find()) {
            return m.group(1)
        }
        return ""
    }

    Map<String,List<String>> call() throws Exception {
        return getBKGNoMap(this.filePath, this.rgex)
    }

    public static void main(String[] args){
        CallableFilePair filePair = new CallableFilePair()
        println(filePair.getBKGNoMap("D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\resource\\COSU-OUTPUT\\IKEA","\\+BN:(.*?)'"))
    }
}
