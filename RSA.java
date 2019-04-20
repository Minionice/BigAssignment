
public class RSA {

	int p, q, n, e, d;
	
	public RSA(int p, int q) {
		this.p = p;
		this.q = q;
		this.n = getn(p, q);
		this.e = gete(p, q);
		this.d = getd(e, p, q);
	}
	
	int euclid(int a, int b) {
	  int tmp;
	  if (b > a) {
	    tmp = b;
	    b = a;
	    a = tmp;
	  }
	  int result = a % b;
	  if (result == 1) return 1;
	  if (result == 0) return b;
	  else return euclid(b, result);
	}

	int getn(int p, int q) {
	  return p * q;
	}

	int gete(int p, int q) {
	  int result;
	  int e = 1;
	  do {
	    e += 2;
	    result = euclid(e, (p-1)*(q-1));
	  } while (result != 1);
	  return e;
	}

	int getd(int e, int p, int q) {
	  int d = 1;
	  while((d * e) % ((p-1)*(q-1)) != 1) {
	    d += 1;
	  }
	  return d;
	}
	
	@Override
	public String toString() {
		return String.format("p = %s; q = %s; n = %s; e = %s; d = %s;", p, q, n, e, d);
	}
}
