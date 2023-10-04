package selfish;
/** text
 * @author will
 * @version 1
*/
public class GameException extends Exception {
    /** text
     * @param msg text
     * @param e text
     */   
    public GameException(String msg, Throwable e){
        super(msg,e);
    }

}