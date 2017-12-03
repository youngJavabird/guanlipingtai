package light.mvc.utils;





import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Socket客户端父类
 * @author wq
 * @date 2015-9-15
 */
public abstract class SocketClient {
	protected String ip;//socket发送IP地址
	protected int port;//socket发送端口
	protected int timeOut;//socket发送超时时间
	
	public SocketClient(String ip,int port,int timeOut){
		this.ip=ip;
		this.port=port;
		this.timeOut=timeOut;
	}
	
	/**
	 * 进行socket发送
	 * @author wq
	 * @date 2015-9-15
	 * @param message
	 * @return
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public abstract String send(byte[] message) throws UnknownHostException, IOException;
		


}