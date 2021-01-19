import java.time.ZonedDateTime;

/**
 * @name: Test <tb>
 * @title: 测试  <tb>
 * @author: cuixinfu@51play.com <tb>
 * @date: 2020/12/22 15:26 <tb>
 */
public class Test {

    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        //System.out.println(now);
        //2020-12-22T15:27:57.999+08:00[Asia/Shanghai]

        String str = "JSCA20120006";
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder builder = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            builder.append(chars[bit]);
            bit = bs[i] & 0x0f;
            builder.append(chars[bit]);
            builder.append(' ');
        }
        System.out.println(builder.toString().trim());

    }

}

