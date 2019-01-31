package service.tools

import com.independentsoft.pst.Folder
import com.independentsoft.pst.PstFile
import org.apache.log4j.Logger

class EmailHnadle {
    private final static Logger logger = Logger.getLogger(this.class)
    public static void main(String[] args){
        PstFile pstFile = new PstFile("D:\\A\\Email\\oll.pst")

//        logger.info(pstFile.getSize())
//        logger.info(pstFile.getEncryptionType())
//        logger.info(pstFile.getMailboxRoot())
//        logger.info(pstFile.getMessageStore().getDisplayName())

        List<Folder> folders = pstFile.getMailboxRoot().getFolders(true)
        Map<Long,String> map = new HashMap<>()

        map.put(pstFile.getMailboxRoot().getId(),pstFile.getMailboxRoot().getDisplayName())

//        for(Folder folder : folders){
//            map.put(folder.getId(),".." + folder.getDisplayName())
//            logger.info(folder.getDisplayName())
//            logger.info(folder.getContainerClass())
//            logger.info(folder.getItemCount())
//            logger.info(map.toString())
//            logger.info("--------------------------")
//        }

        pstFile.getMailboxRoot().getFolders(true)[3].getItems().eachWithIndex{ current_Item, current_Item_idx ->
            logger.info(current_Item.getId())
            logger.info(current_Item.getSubject())
            logger.info(current_Item.getDisplayTo())
            logger.info(current_Item.getSenderName())
            logger.info(current_Item.getSenderEmailAddress())
            logger.info(current_Item.getBody())
            logger.info("-----------------------------")
        }
    }
}
