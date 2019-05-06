package nep.tools.cosco

import com.google.common.io.Files

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

class FutureTaskForFilePair {

    public static void main(String[] args){
        def coscoEDISource = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\resource\\COSU-OUTPUT\\IKEA"
        def cs2XMLSource = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\resource\\CS2XML\\C"

        CallableFilePair coscoEDIpair = new CallableFilePair(coscoEDISource,"\\+BN:(.*?)'")
        CallableFilePair cs2XMLpair = new CallableFilePair(cs2XMLSource,"<CarrierBookingNumber>(.*?)</CarrierBookingNumber>")

        FutureTask<Map<String,List<String>>> coscoEDIpairCall = new FutureTask<>(coscoEDIpair)
        FutureTask<Map<String,List<String>>> cs2XMLpairCall = new FutureTask<>(cs2XMLpair)

        ExecutorService executor = Executors.newFixedThreadPool(2)
        executor.execute(coscoEDIpairCall)
        executor.execute(cs2XMLpairCall)

        String coscoDEIDest = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\ExpectedComplete\\"
        String coscoDEIUnpairDest = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\ExpectedComplete-unpair\\"
        String cs2XMLDest = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\InputData\\"
        String cs2XMLUnpairDest = "D:\\1_B2BEDI_Revamp\\BC\\OUT_D96B\\IKEA-COSU\\InputData-unpair\\"
        while(true){
            if(coscoEDIpairCall.isDone() && cs2XMLpairCall.isDone()){
                println("All pair process done ....")
                executor.shutdown()

                CallableFilePair.BKG_NO.eachWithIndex{ curr_BKGNo, curr_BKGNo_idx ->
                    if(coscoEDIpairCall.get().get(curr_BKGNo) && cs2XMLpairCall.get().get(curr_BKGNo)){
                        int maxSize = Math.max(coscoEDIpairCall.get().get(curr_BKGNo).size(), cs2XMLpairCall.get().get(curr_BKGNo).size())
                        int minSize = Math.min(coscoEDIpairCall.get().get(curr_BKGNo).size(), cs2XMLpairCall.get().get(curr_BKGNo).size())
                            for(int i = 0;i < maxSize; i++){
                                String prefix = "${curr_BKGNo_idx}_${i}_${curr_BKGNo}_"
                                //for cosco EDI
                                if(coscoEDIpairCall.get().get(curr_BKGNo).size() > minSize &&  i >= minSize){
                                    Files.copy(new File(coscoEDIpairCall.get().get(curr_BKGNo)[i]), new File(coscoDEIUnpairDest + prefix + new File(coscoEDIpairCall.get().get(curr_BKGNo)[i]).getName()))
                                }else if(coscoEDIpairCall.get().get(curr_BKGNo).size() > i){
                                    Files.copy(new File(coscoEDIpairCall.get().get(curr_BKGNo)[i]), new File(coscoDEIDest + prefix + new File(coscoEDIpairCall.get().get(curr_BKGNo)[i]).getName()))
                                }
                                //for CS2XML
                                if(cs2XMLpairCall.get().get(curr_BKGNo).size() > minSize &&  i >= minSize){
                                    Files.copy(new File(cs2XMLpairCall.get().get(curr_BKGNo)[i]), new File(cs2XMLUnpairDest + prefix + new File(cs2XMLpairCall.get().get(curr_BKGNo)[i]).getName()))
                                }else if(cs2XMLpairCall.get().get(curr_BKGNo).size() > i){
                                    Files.copy(new File(cs2XMLpairCall.get().get(curr_BKGNo)[i]), new File(cs2XMLDest + prefix + new File(cs2XMLpairCall.get().get(curr_BKGNo)[i]).getName()))
                                }
                            }
                    }
                }
                return
            }
        }

    }
}
