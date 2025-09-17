package demos;
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class HeapDumpDemo {
    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Customer customer = new Customer("John Doe", 30, 1000.50);
        server.invoke(
            new ObjectName("com.sun.management:type=HotSpotDiagnostic"),
            "dumpHeap",
            new Object[] {"heapdump.hprof", true},
            new String[] {"java.lang.String", "boolean"}
        );
        System.out.println("Heap dump written to heapdump.hprof");
    }

    
}
class Customer{
        String name;
        int age;
        double balance;

        Customer(String name, int age, double balance) {
            this.name = name;
            this.age = age;
            this.balance = balance;
        }
        
        @Override
        public String toString() {
            return "Customer{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", balance=" + balance +
                    '}';
        }
        //getters and setters
        public String getName() {
            return name;
        }


        public int getAge() {
            return age;
        }

        public double getBalance() {
            return balance;
        }

    }