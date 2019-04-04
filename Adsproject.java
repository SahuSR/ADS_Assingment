import java.util.Scanner;

public class Adsproject {
    
    private int T;

     class bNode {
        int n;
        int key[] = new int[2*T-1];
        bNode child[] = new bNode[2*T];
        boolean leaf = true;

        public int Find(int k){
            for (int i = 0; i < this.n; i++) {
                if (this.key[i] == k) {
                    return i;
                }
            }
            return -1;
        };
    }
    static bNode root;
    static bNode temp;
    Adsproject(int _T) {
       T = _T;
       root = new bNode();
       root.n = 0;
       root.leaf = true;
    }

    static int height=0,ht=0,num=0;
    private bNode Search (bNode x,int key) {
        int i = 0;
        if (x == null) return x;
        for (i = 0; i < x.n; i++) {
            if (key < x.key[i]) {
                break;
            }
            if (key == x.key[i]) {
                num=i+1;
                return x;
            }
        }
        if (x.leaf) {
            return null;
        } else {
            ht++;
            return Search(x.child[i],key);
        }
    }
    private bNode Search(int key){
        return Search(root,key);
    }
    private void Split (bNode x, int pos, bNode y) {
        bNode z = new bNode();
        z.leaf = y.leaf;
        z.n = T - 1;
        for (int j = 0; j < T - 1; j++) {
            z.key[j] = y.key[j+T];
        }
        if (!y.leaf) {
            for (int j = 0; j < T; j++) {
                z.child[j] = y.child[j+T];
            }
        }
        y.n = T-1;
        for (int j = x.n; j >= pos+1; j--) {
            x.child[j+1] = x.child[j];
        }
        x.child[pos+1] = z;

        for (int j = x.n-1; j >= pos; j--) {
            x.key[j+1] = x.key[j];
        }
        x.key[pos] = y.key[T-1];
        x.n = x.n + 1;
    }

    public void Insert (final int key) {
        bNode r = root;
        if (r.n == 2*T - 1 ) {
            bNode s = new bNode();
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            Split(s,0,r);
            _Insert(s,key);
        }else {
            _Insert(r,key);
        }
    }

    final private void _Insert (bNode x, int k) {

            if (x.leaf) {
                    int i = 0;
                    for (i = x.n-1; i >= 0 && k < x.key[i]; i--) {
                            x.key[i+1] = x.key[i];
                    }
                    x.key[i+1] = k;
                    x.n = x.n + 1;
            } else {
                    int i = 0;
                    for (i = x.n-1; i >= 0 && k < x.key[i]; i--) {};
                    i++;
                    bNode tmp = x.child[i];
                    if (tmp.n == 2*T -1) {
                            Split(x,i,tmp);
                            if ( k > x.key[i]) {
                                    i++;
                            }
                    }
                    _Insert(x.child[i], k);
            }

    }

    public void Show () {
        Show(root);
    }

    private void Show (bNode x) {
        System.out.print("Height: " + height + "-> " );
        for ( int i = 0; i < x.n; i++) {
            System.out.print(x.key[i]+ " ");
        }
        System.out.println();
        if (!x.leaf) {
            height++;
            for (int i = 0; i <  x.n + 1; i++) {
                Show(x.child[i]);
            }
        }
    }



    private void Remove (bNode x, int key) {
            int pos = x.Find(key);
            if (pos != -1) {
                    if (x.leaf) {
                            int i = 0;
                            for (i = 0; i < x.n && x.key[i] != key; i++) {};
                            for (; i < x.n; i++) {
                                    if (i != 2*T - 2) {
                                            x.key[i] = x.key[i+1];
                                    }
                            }
                            x.n--;
                            return;
                    }
                    if (!x.leaf) {
                            bNode pred = x.child[pos];
                            int predKey = 0;
                            if (pred.n >= T) {
                                    for (;;) {
                                            if (pred.leaf) {
                                                    System.out.println(pred.n);
                                                    predKey = pred.key[pred.n - 1];
                                                    break;
                                            } else {
                                                    pred = pred.child[pred.n];
                                            }
                                    }
                                    Remove (pred, predKey);
                                    x.key[pos] = predKey;
                                    return;
                            }
                             bNode nextNode = x.child[pos+1];
                            if (nextNode.n >= T) {
                                    int nextKey = nextNode.key[0];
                                    if (!nextNode.leaf) {
                                            nextNode = nextNode.child[0];
                                            for (;;) {
                                                    if (nextNode.leaf) {
                                                            nextKey = nextNode.key[nextNode.n-1];
                                                            break;
                                                    } else {
                                                            nextNode = nextNode.child[nextNode.n];
                                                    }
                                            }
                                    }
                                    Remove(nextNode, nextKey);
                                    x.key[pos] = nextKey;
                                    return;
                            }
                            int temp = pred.n + 1;
                            pred.key[pred.n++] = x.key[pos];
                            for (int i = 0, j = pred.n; i < nextNode.n; i++) {
                                    pred.key[j++] = nextNode.key[i];
                                    pred.n++;
                            }
                            for (int i = 0; i < nextNode.n+1; i++) {
                                    pred.child[temp++] = nextNode.child[i];
                            }

                            x.child[pos] = pred;
                            for (int i = pos; i < x.n; i++) {
                                    if (i != 2*T - 2) {
                                            x.key[i] = x.key[i+1];
                                    }
                            }
                            for (int i = pos+1; i < x.n+1; i++) {
                                    if (i != 2*T - 1) {
                                            x.child[i] = x.child[i+1];
                                    }
                            }
                            x.n--;
                            if (x.n == 0) {
                                    if (x == root) {
                                            root = x.child[0];
                                    }
                                    x = x.child[0];
                            }
                            Remove(pred,key);
                            return;
                    }
            } else {
                    for (pos = 0; pos < x.n; pos++) {
                            if (x.key[pos] > key) {
                                    break;
                            }
                    }
                    bNode tmp = x.child[pos];
                    if (tmp.n >= T) {
                            Remove (tmp,key);
                            return;
                    }
                    if (true) {
                            bNode nb = null;
                            int devider = -1;
                            if (pos != x.n && x.child[pos+1].n >= T) {
                                    devider = x.key[pos];
                                    nb = x.child[pos+1];
                                    x.key[pos] = nb.key[0];
                                    tmp.key[tmp.n++] = devider;
                                    tmp.child[tmp.n] = nb.child[0];
                                    for (int i = 1; i < nb.n; i++) {
                                            nb.key[i-1] = nb.key[i];
                                    }
                                    for (int i = 1; i <= nb.n; i++) {
                                            nb.child[i-1] = nb.child[i];
                                    }
                                    nb.n--;
                                    Remove(tmp,key);
                                      return;
                            } else if (pos != 0 && x.child[pos-1].n >= T) {
                                    devider = x.key[pos-1];
                                    nb = x.child[pos-1];
                                    x.key[pos-1] = nb.key[nb.n-1];
                                    bNode child = nb.child[nb.n];
                                    nb.n--;
                                    for(int i = tmp.n; i > 0; i--) {
                                            tmp.key[i] = tmp.key[i-1];
                                    }
                                    tmp.key[0] = devider;
                                    for(int i = tmp.n + 1; i > 0; i--) {
                                            tmp.child[i] = tmp.child[i-1];
                                    }
                                    tmp.child[0] = child;
                                    tmp.n++;
                                    Remove(tmp,key);
                                    return;
                            } else {
                                    bNode lt = null;
                                    bNode rt = null;
                                    boolean last = false;
                                    if (pos != x.n) {
                                            devider = x.key[pos];
                                            lt = x.child[pos]; 
                                            rt = x.child[pos+1];
                                    } else {
                                            devider = x.key[pos-1];
                                            rt = x.child[pos];
                                            lt = x.child[pos-1];
                                            last = true;
                                            pos--;
                                    }

                                    for (int i = pos; i < x.n-1; i++) {
                                            x.key[i] = x.key[i+1];
                                    }
                                    for(int i = pos+1; i < x.n; i++) {
                                            x.child[i] = x.child[i+1];
                                    }
                                    x.n--;
                                    lt.key[lt.n++] = devider;
                                    int numChild = 0;
                                    for (int i = 0, j = lt.n; i < rt.n+1; i++,j++) {
                                            if (i < rt.n) {
                                                    lt.key[j] = rt.key[i];
                                            }
                                            lt.child[j] = rt.child[i];
                                    }
                                    lt.n += rt.n;
                                    if (x.n == 0) {
                                            if (x == root) {
                                                    root = x.child[0];
                                            }
                                            x = x.child[0];
                                    }
                                    Remove(lt,key);
                                    return;
                            }
                    }
            }
    }
    public void Remove (int key) {
            bNode x = Search(root, key);
            if (x == null) {
                return;
            }
            Remove(root,key);
    }

    public boolean Contain(int k) {
        if (this.Search(root, k) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    Adsproject() 
    { 
        root = null; 
    } 
  
    public static void main(String[] args) 
    { 
        Scanner sc = new Scanner(System.in);
        int process;
        System.out.println("Enter no of key in single node of Btree");
        int value;
        value = sc.nextInt();
        Adsproject  bt = new Adsproject((value+1)/2);
        do{
            System.out.println("1.insertion \t 2.deletion \t 3.Search \t 4.Display (-999 to exit)");
            process = sc.nextInt();
            switch(process){
                case 1: System.out.println("Enter the value to insert"); 
                        value = sc.nextInt();
                        bt.Insert(value);
                        break;
                case 2: System.out.println("Enter the value to delete"); 
                        value = sc.nextInt();
                        bt.Remove(value);
                        break;
                case 3: System.out.println("Enter the element to be searched :");
                        int key=sc.nextInt();
                        temp=bt.Search(key);
                        System.out.println("Key found at height: "+ht+" element no: "+num);
                        num=0;ht=0;
                        break;
                case 4:height=0;
                       bt.Show();
                       System.out.println("");
                       break;
                default:break;
            }
        }while(process!=-999);
    } 
} 