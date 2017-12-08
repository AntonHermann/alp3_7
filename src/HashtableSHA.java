import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class HashtableSHA<K extends Byte, V> extends Hashtable<K, V> {
	public static void main(String[] args) {
		HashtableSHA<Byte, String> h = new HashtableSHA<>();
		run(h);
	}

	private MessageDigest md;

	public HashtableSHA() {
		this(10);
	}
	public HashtableSHA(int capacity) {
		super(capacity);
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e);
		}
	}

	@Override
	protected int genIndex(K key) {
		md.reset();
		md.update(key);
		byte[] hash = md.digest();
		int hc = hash[0] % capacity();
		if (hc < 0) {
			return -hc;
		} else {
			return hc;
		}
	}
	@Override
	protected String hash(K key) {
		md.reset();
		md.update(key);
		byte[] hash = md.digest();

		Formatter form = new Formatter();
    	for (int i = 0; i < hash.length; i++) {
    		form.format("%02x", hash[i]);
    	}
    	return form.toString();
	}
}