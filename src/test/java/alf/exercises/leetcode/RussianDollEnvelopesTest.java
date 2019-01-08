package alf.exercises.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RussianDollEnvelopesTest {

    RussianDollEnvelopes ex = new RussianDollEnvelopes();

    @Test
    public void maxEnvelopes() {

        int[][] input = new int[4][];
        input[0] = new int[]{5,4};
        input[1] = new int[]{6,4};
        input[2] = new int[]{6,7};
        input[3] = new int[]{2,3};

        assertEquals(3, ex.maxEnvelopes(input));
    }

    @Test
    public void test2() {

        //[[30,50],[12,2],[3,4],[12,15]]
        int[][] input = new int[4][];
        input[0] = new int[]{30,50};
        input[1] = new int[]{12,2};
        input[2] = new int[]{3,4};
        input[3] = new int[]{12,15};

        assertEquals(3, ex.maxEnvelopes(input));
    }

    @Test
    public void test3() {

        //[[1,1]]
        int[][] input = new int[1][];
        input[0] = new int[]{1,1};

        assertEquals(1, ex.maxEnvelopes(input));
    }

    @Test
    public void test4() {

        //[[]]
        int[][] input = new int[1][];
        input[0] = new int[1];

        assertEquals(0, ex.maxEnvelopes(input));
    }

    @Test
    public void test5() {

        //[[2,100],[3,200],[4,300],[5,500],[5,400],[5,250],[6,370],[6,360],[7,380]]
        int[][] input = new int[9][];
        input[0] = new int[]{2,100};
        input[1] = new int[]{3,200};
        input[2] = new int[]{4,300};
        input[3] = new int[]{5,500};
        input[4] = new int[]{5,400};
        input[5] = new int[]{5,250};
        input[6] = new int[]{6,370};
        input[7] = new int[]{6,360};
        input[8] = new int[]{7,380};

        assertEquals(5, ex.maxEnvelopes(input));
    }

    @Test
    public void test6() {

        //[[46,89],[50,53],[52,68],[72,45],[77,81]]
        int[][] input = new int[5][];
        input[0] = new int[]{46,89};
        input[1] = new int[]{50,53};
        input[2] = new int[]{52,68};
        input[3] = new int[]{72,45};
        input[4] = new int[]{77,81};

        assertEquals(3, ex.maxEnvelopes(input));
    }

    @Test
    public void test7() {

        int[][] input = parse("[[15,8],[2,20],[2,14],[4,17],[8,19],[8,9],[5,7],[11,19],[8,11],[13,11],[2,13],[11,19],[8,11],[13,11],[2,13],[11,19],[16,1],[18,13],[14,17],[18,19]]");

        assertEquals(5, ex.maxEnvelopes(input));
    }

    @Test
    public void test8() {

        int[][] input = parse("[[1,1],[1,1],[1,1]]");

        assertEquals(1, ex.maxEnvelopes(input));
    }

    @Test
    public void test9() {

        int[][] input = parse("[[8,11],[14,4],[7,15],[8,8]]");

        assertEquals(1, ex.maxEnvelopes(input));
    }

    @Test
    public void test10() {

        int[][] input = parse("[[856,533],[583,772],[980,524],[203,666],[987,151],[274,802],[982,85],[359,160],[58,823]," +
                "[512,381],[796,655],[341,427],[145,114],[76,306],[760,929],[836,751],[922,678],[128,317],[185,953]," +
                "[115,845],[829,991],[93,694],[317,434],[818,571],[352,638],[926,780],[819,995],[54,69],[191,392]," +
                "[377,180],[669,952],[588,920],[335,316],[48,769],[188,661],[916,933],[674,308],[356,556],[350,249]," +
                "[686,851],[600,178],[849,439],[597,181],[80,382],[647,105],[4,836],[901,907],[595,347],[214,335]," +
                "[956,382],[77,979],[489,365],[80,220],[859,270],[676,665],[636,46],[906,457],[522,769],[2,758]," +
                "[206,586],[444,904],[912,370],[64,871],[59,409],[599,238],[437,58],[309,767],[258,440],[922,369]," +
                "[848,650],[478,76],[84,704],[314,207],[138,823],[994,764],[604,595],[537,876],[877,253],[945,185]," +
                "[623,497],[968,633],[172,705],[577,388],[819,763],[409,905],[275,532],[729,593],[547,226],[445,495]," +
                "[398,544],[243,500],[308,24],[652,452],[93,885],[75,884],[243,113],[600,555],[756,596],[892,762]," +
                "[402,653],[916,975],[770,220],[455,579],[889,68],[306,899],[567,290],[809,653],[92,329],[370,861]," +
                "[632,754],[321,689],[190,812],[88,701],[79,310],[917,91],[751,480],[750,39],[781,978],[778,912]," +
                "[946,559],[529,621],[55,295],[473,748],[646,854],[930,913],[116,734],[647,812],[426,172],[122,14]," +
                "[522,843],[88,308],[719,602],[712,928],[303,890],[973,886],[276,354],[660,720],[708,387],[776,605]," +
                "[653,815],[448,285],[549,959],[139,365],[74,952],[372,424],[642,504],[361,901],[620,612],[313,301]," +
                "[397,225],[446,716],[17,361],[160,812],[171,529],[180,482],[454,600],[228,872],[204,492],[607,889]," +
                "[86,79],[494,78],[442,404],[462,127],[935,402],[509,649],[458,941],[219,444],[306,57],[674,617]," +
                "[79,652],[73,735],[900,756],[649,294],[982,754],[521,439],[356,265],[240,533],[865,44],[744,379]," +
                "[97,454],[65,480],[544,191],[18,191],[503,38],[696,658],[61,884],[793,984],[383,364],[280,467]," +
                "[888,662],[133,643],[365,512],[610,975],[98,584],[40,177],[548,102],[80,98],[986,951],[264,258]," +
                "[583,734],[353,322],[427,551],[80,660],[273,609],[980,871],[739,802],[366,836],[55,509],[889,720]," +
                "[857,661],[48,489],[119,26],[31,180],[472,673],[960,951],[383,500],[928,351],[848,705],[969,766]," +
                "[311,714],[861,230],[34,596],[38,642],[1,955],[698,846],[784,791],[760,344],[677,239],[969,191]," +
                "[539,644],[470,418],[289,357],[269,446],[668,245],[293,719],[937,103],[575,297],[874,656],[714,257]," +
                "[934,396],[109,904],[89,635],[374,545],[316,587],[158,121],[901,969],[284,564],[666,568],[993,409]," +
                "[370,637],[443,694],[576,160],[262,357],[590,729],[194,976],[743,376],[348,80],[669,527],[338,953]," +
                "[236,785],[144,460],[438,457],[517,951],[545,647],[158,556],[905,591],[793,609],[571,643],[9,850]," +
                "[581,490],[804,394],[635,483],[457,30],[42,621],[65,137],[424,864],[536,455],[59,492],[645,734]," +
                "[892,571],[762,593],[608,384],[558,257],[692,420],[973,203],[531,51],[349,861],[804,649],[3,611]," +
                "[6,468],[298,568],[651,767],[251,142],[173,974],[117,728],[326,562],[894,288],[814,555],[420,771]," +
                "[20,775],[445,247],[243,592],[186,173],[101,800],[590,876],[515,534],[73,540],[333,215],[902,394]," +
                "[640,787],[596,298],[984,712],[307,378],[540,646],[473,743],[340,387],[756,217],[139,493],[9,742]," +
                "[195,25],[763,823],[451,693],[24,298],[645,595],[224,770],[976,41],[832,78],[599,705],[487,734]," +
                "[818,134],[225,431],[380,566],[395,680],[294,320],[915,201],[553,480],[318,42],[627,94],[164,959]," +
                "[92,715],[588,689],[734,983],[976,334],[846,573],[676,521],[449,69],[745,810],[961,722],[416,409]," +
                "[135,406],[234,357],[873,61],[20,521],[525,31],[659,688],[424,554],[203,315],[16,240],[288,273]," +
                "[281,623],[651,659],[939,32],[732,373],[778,728],[340,432],[335,80],[33,835],[835,651],[317,156]," +
                "[284,119],[543,159],[719,820],[961,424],[88,178],[621,146],[594,649],[659,433],[527,441],[118,160]," +
                "[92,217],[489,38],[18,359],[833,136],[470,897],[106,123],[831,674],[181,191],[892,780],[377,779]," +
                "[608,618],[618,423],[180,323],[390,803],[562,412],[107,905],[902,281],[718,540],[16,966],[678,455]" +
                ",[597,135],[840,7],[886,45],[719,937],[890,173]]");

        assertEquals(1, ex.maxEnvelopes(input));
    }

    private int[][] parse(String s) {
        String[] split = s.replace("[", "").replace("]", "").split(",");
        int[][] result = new int[split.length / 2][];
        int ix = 0;
        for (int i = 0, j=1; i < split.length; i = i +2, j=j+2) {
            result[ix] = new int[]{Integer.valueOf(split[i]), Integer.valueOf(split[j])};
            ix++;
        }
        return result;
    }
//
//    @Test
//    public void testFirst() {
//        List<RussianDollEnvelopes.Env> list = Arrays.asList(
//                RussianDollEnvelopes.Env.of(1,1),
//                RussianDollEnvelopes.Env.of(2,2),
//                RussianDollEnvelopes.Env.of(3,3));
//
//        assertTrue(ex.first(list).isPresent());
//        assertEquals(RussianDollEnvelopes.Env.of(1,1), ex.first(list).orElse(null));
//
//        assertFalse(ex.first(Collections.emptyList()).isPresent());
//    }
//
//    @Test
//    public void testLast() {
//        List<RussianDollEnvelopes.Env> list = Arrays.asList(
//                RussianDollEnvelopes.Env.of(1,1),
//                RussianDollEnvelopes.Env.of(2,2),
//                RussianDollEnvelopes.Env.of(3,3));
//
//        assertTrue(ex.last(list).isPresent());
//        assertEquals(RussianDollEnvelopes.Env.of(3,3), ex.last(list).orElse(null));
//
//        assertFalse(ex.last(Collections.emptyList()).isPresent());
//    }
//
//    @Test
//    public void testRest() {
//        List<RussianDollEnvelopes.Env> list = Arrays.asList(
//                RussianDollEnvelopes.Env.of(1,1),
//                RussianDollEnvelopes.Env.of(2,2),
//                RussianDollEnvelopes.Env.of(3,3));
//
//        assertEquals(2, ex.rest(list).size());
//        assertEquals(RussianDollEnvelopes.Env.of(2,2), ex.rest(list).get(0));
//
//        assertEquals(1, ex.rest(ex.rest(list)).size());
//        assertEquals(RussianDollEnvelopes.Env.of(3,3), ex.rest(ex.rest(list)).get(0));
//
//        assertEquals(0, ex.rest(ex.rest(ex.rest(list))).size());
//    }
//
//    @Test
//    public void testNodeDepth() {
//        RussianDollEnvelopes.Node n = RussianDollEnvelopes.Node.of(RussianDollEnvelopes.Env.of(1,1));
//        assertEquals(1, n.depth());
//
//        RussianDollEnvelopes.Node n2 =RussianDollEnvelopes.Node.of(RussianDollEnvelopes.Env.of(2,2));
//        n.addNestor(n2);
//        assertEquals(2, n.depth());
//
//        RussianDollEnvelopes.Node n3 = RussianDollEnvelopes.Node.of(RussianDollEnvelopes.Env.of(3,3));
//        n2.addNestor(n3);
//        assertEquals(3, n.depth());
//
//        RussianDollEnvelopes.Node n2b =RussianDollEnvelopes.Node.of(RussianDollEnvelopes.Env.of(2,3));
//        n.addNestor(n2b);
//
//        RussianDollEnvelopes.Node n3b = RussianDollEnvelopes.Node.of(RussianDollEnvelopes.Env.of(3,4));
//        n2b.addNestor(n3b);
//
//        RussianDollEnvelopes.Node n4b = RussianDollEnvelopes.Node.of(RussianDollEnvelopes.Env.of(4,5));
//        n3b.addNestor(n4b);
//        assertEquals(4, n.depth());
//
//        System.out.println(n);
//    }

//    @Test
//    public void testNodeCopy() {
//        RussianDollEnvelopes.Node n = RussianDollEnvelopes.Node.of(RussianDollEnvelopes.Env.of(1,1));
//        n.concat(RussianDollEnvelopes.Env.of(2,2));
//        n.concat(RussianDollEnvelopes.Env.of(3,3));
//
//        RussianDollEnvelopes.Node n2 = n.copy();
//        assertEquals(3, n2.depth());
//    }
}