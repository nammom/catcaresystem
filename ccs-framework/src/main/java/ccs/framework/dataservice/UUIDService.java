package ccs.framework.dataservice;

import java.util.UUID;

import org.springframework.stereotype.Service;

import ccs.framework.util.MacAddressUtility;

@Service(value = "uuidService")
public class UUIDService implements IdGnrService {

	public static final Object lock = new Object();
	
	private static long lastTime;
	private static long clockSequence = 0;
	private static final long hostIdentifier = MacAddressUtility.getMacAddressAsLong();
	
	
	public void initialize() throws Exception{
		// 초기화 수행.
	}
	
	public void destroy() throws Exception{
		//소멸자 호출
	}
	
	public String getId() throws Exception{
		return getUUID();
	}
	
	private String getUUID(){
		//return UUID.randomUUID().toString();
		return generateIdFromTimestamp(System.currentTimeMillis(),hostIdentifier).toString();
	}
	
	private final static UUID generateIdFromTimestamp(long currentTimeMillis, long hostId) {
		long time;
		synchronized (lock) {
			if (currentTimeMillis > lastTime) {
				lastTime = currentTimeMillis;
				clockSequence = 0;
			} else {
				++clockSequence;
			}
		}

		time = currentTimeMillis;

		// low Time
		time = currentTimeMillis << 32;

		// mid Time
		time |= ((currentTimeMillis & 0xFFFF00000000L) >> 16);

		// hi Time
		time |= 0x1000 | ((currentTimeMillis >> 48) & 0x0FFF);

		long clockSequenceHi = clockSequence;
		clockSequenceHi <<= 48;
		long lsb = (hostId != 0L ? clockSequenceHi | hostId : clockSequenceHi | hostIdentifier);

		return new UUID(time, lsb);
	}
}
