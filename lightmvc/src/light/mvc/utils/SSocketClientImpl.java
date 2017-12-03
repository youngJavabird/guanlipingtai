package light.mvc.utils;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;





/**
 * socket短连接客户端实现
 * @author wq
 * @date 2015-9-15
 */
public class SSocketClientImpl extends SocketClient {
	private final Log log = LogFactory.getLog(this.getClass());
	public SSocketClientImpl(String ip, int port, int timeOut) {
		super(ip, port, timeOut);
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
	@Override
	public String send(byte[] message) throws UnknownHostException, IOException{
		Socket socket=null;
		OutputStream out=null;
		InputStream in=null;
		byte[] ms=new byte[0];//客户端请求数据
		String mString="";
		try{
			socket = new Socket(ip, port);
			socket.setSoTimeout(timeOut);
			out = socket.getOutputStream();
			in = socket.getInputStream();
			out.write(message);
			out.flush();
			// 得到客户端请求字符串
			/*byte[] items=new byte[0];//当前读取数据
			int itemLen=4096;//每次最大读取长度
*/			/*int len =0;//实际读取长度
*/			/*do {*/
				
				 InputStreamReader streamReader = new InputStreamReader(in,"UTF-8");  
				 //链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder  
			        BufferedReader reader = new BufferedReader(streamReader);  
			        String advice ="";  
			          StringBuffer a = new StringBuffer();
			        while (!((advice=reader.readLine())==null)) {
						a.append(advice);
					} 
			        mString= a.toString();
				/*byte[] item=new byte[4096];//本次读取数据
				len = in.read(item);//本次读取数据大小
				if(len<=0){
					break;
				}
				ms=new byte[ms.length+len];//客户端请求数据=上次读取数据+本次读取数据
				System.arraycopy(items, 0, ms, 0, items.length);
				System.arraycopy(item, 0, ms, items.length,ms.length);
				items=new byte[ms.length];
				System.arraycopy(ms, 0, items, 0, items.length);
			 while (len==itemLen);*/
		} catch (Exception e) {
//			 log.info("--socket 发送异常！"+e.getMessage());
			return "{\"retcode\":\"15\",\"result\":\"网络繁忙，请稍后重试\"}";
		}finally{
			try {
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
				if(socket!=null){
//					socket.shutdownInput();
//					socket.shutdownOutput();
					socket.close();
				}
			} catch (Throwable e) {
				log.error("关闭连接异常：",e);
			}
		}
		return mString;
	}
}
