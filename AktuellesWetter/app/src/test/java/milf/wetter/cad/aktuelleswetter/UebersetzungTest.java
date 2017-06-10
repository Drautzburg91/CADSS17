package milf.wetter.cad.aktuelleswetter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UebersetzungTest {
    public Uebersetzung ueber = new Uebersetzung();
    @Test
   public void caseOne(){
        Assert.assertTrue(ueber.uebersetzen(200).equals("Gewitter mit Leichtregen"));
        Assert.assertFalse(ueber.uebersetzen(200).equals("Gewitter mit Regen"));
    }
    @Test
    public void caseTwo(){
        Assert.assertTrue(ueber.uebersetzen(201).equals("Gewitter mit Regen"));
        Assert.assertFalse(ueber.uebersetzen(201).equals("andauernder Starkregen"));
    }
    @Test
    public void caseThree(){
        Assert.assertTrue(ueber.uebersetzen(202).equals("Gewitter mit Starkregen"));
        Assert.assertFalse(ueber.uebersetzen(202).equals("andauernder Starkregen"));
    }
    @Test
    public void caseFour(){
        Assert.assertTrue(ueber.uebersetzen(210).equals("Leichtes Gewitter"));
        Assert.assertFalse(ueber.uebersetzen(210).equals("andauernder Starkregen"));
    }
    @Test
    public void caseFive(){
        Assert.assertTrue(ueber.uebersetzen(211).equals("Gewitter"));
        Assert.assertFalse(ueber.uebersetzen(211).equals("Starkes Gewitter"));
    }
    @Test
    public void caseSix(){
        Assert.assertTrue(ueber.uebersetzen(212).equals("Starkes Gewitter"));
        Assert.assertFalse(ueber.uebersetzen(212).equals("andauernder Starkregen"));
    }
    @Test
    public void caseSeven(){
        Assert.assertTrue(ueber.uebersetzen(221).equals("vereinzelt Schauer und Gewitter"));
        Assert.assertFalse(ueber.uebersetzen(221).equals("andauernder Starkregen"));
    }
    @Test
    public void caseEight(){
        Assert.assertTrue(ueber.uebersetzen(230).equals("Gewitter mit Nieselregen"));
        Assert.assertFalse(ueber.uebersetzen(230).equals("andauernder Starkregen"));
    }
    @Test
    public void caseNine(){
        Assert.assertTrue(ueber.uebersetzen(231).equals("Gewitter mit Regen"));
        Assert.assertFalse(ueber.uebersetzen(231).equals("andauernder Starkregen"));
    }
    @Test
    public void caseTen(){
        Assert.assertTrue(ueber.uebersetzen(232).equals("Gewitter mit Starkregen"));
        Assert.assertFalse(ueber.uebersetzen(232).equals("andauernder Starkregen"));
    }
    @Test
    public void caseEleven(){
        Assert.assertTrue(ueber.uebersetzen(300).equals("Nieselregen"));
        Assert.assertFalse(ueber.uebersetzen(300).equals("andauernder Starkregen"));
    }
    @Test
    public void caseTwelve(){
        Assert.assertTrue(ueber.uebersetzen(301).equals("Nieselregen"));
        Assert.assertFalse(ueber.uebersetzen(301).equals("andauernder Starkregen"));
    }
    @Test
    public void caseThirteen(){
        Assert.assertTrue(ueber.uebersetzen(302).equals("Starker Nieselregen"));
        Assert.assertFalse(ueber.uebersetzen(302).equals("andauernder Starkregen"));
    }
    @Test
    public void caseFourteen(){
        Assert.assertTrue(ueber.uebersetzen(310).equals("Leichter Nieselregen"));
        Assert.assertFalse(ueber.uebersetzen(310).equals("andauernder Starkregen"));
    }
    @Test
    public void caseFifteen(){
        Assert.assertTrue(ueber.uebersetzen(311).equals("Regenegen"));
        Assert.assertFalse(ueber.uebersetzen(311).equals("andauernder Starkregen"));
    }
    @Test
    public void caseSeventeen(){
        Assert.assertTrue(ueber.uebersetzen(312).equals("Starker Sprühregel"));
        Assert.assertFalse(ueber.uebersetzen(312).equals("andauernder Starkregen"));
    }
    @Test
    public void caseEighteen(){
        Assert.assertTrue(ueber.uebersetzen(313).equals("Starkregen mit Nieselregen"));
        Assert.assertFalse(ueber.uebersetzen(313).equals("andauernder Starkregen"));
    }
    @Test
    public void caseNineteen(){
        Assert.assertTrue(ueber.uebersetzen(314).equals("Starkregen"));
        Assert.assertFalse(ueber.uebersetzen(314).equals("andauernder Starkregen"));
    }
    @Test
    public void caseTwenty(){
        Assert.assertTrue(ueber.uebersetzen(321).equals("andauernder Starkregen"));
        Assert.assertFalse(ueber.uebersetzen(321).equals("Starkregen"));
    }
    @Test
    public void caseTwentyOne(){
        Assert.assertTrue(ueber.uebersetzen(500).equals("Leichter Regen"));
        Assert.assertFalse(ueber.uebersetzen(500).equals("Starkregen"));
    }
    @Test
    public void caseTwentyTwo(){
        Assert.assertTrue(ueber.uebersetzen(501).equals("moderater Regen"));
        Assert.assertFalse(ueber.uebersetzen(501).equals("Starkregen"));
    }
    @Test
    public void caseTwentyThree(){
        Assert.assertTrue(ueber.uebersetzen(502).equals("Regen"));
        Assert.assertFalse(ueber.uebersetzen(502).equals("Starkregen"));
    }
    @Test
    public void caseTwentyFour(){
        Assert.assertTrue(ueber.uebersetzen(503).equals("Extremer Starkregen"));
        Assert.assertFalse(ueber.uebersetzen(503).equals("Starkregen"));
    }
    @Test
    public void caseTwentyFive(){
        Assert.assertTrue(ueber.uebersetzen(504).equals("extremer Regen"));
        Assert.assertFalse(ueber.uebersetzen(504).equals("Starkregen"));
    }
    @Test
    public void caseTwentySix(){
        Assert.assertTrue(ueber.uebersetzen(511).equals("gefrierender Regen"));
        Assert.assertFalse(ueber.uebersetzen(511).equals("Starkregen"));
    }
    @Test
    public void caseTwentySeven(){
        Assert.assertTrue(ueber.uebersetzen(520).equals("Starkregen"));
        Assert.assertFalse(ueber.uebersetzen(520).equals("gefrierender Starkregen"));
    }
    @Test
    public void caseTwentyEight(){
        Assert.assertTrue(ueber.uebersetzen(521).equals("leichter Regen"));
        Assert.assertFalse(ueber.uebersetzen(521).equals("gefrierender Starkregen"));
    }
    @Test
    public void caseTwentyNine(){
        Assert.assertTrue(ueber.uebersetzen(522).equals("extremer Starkregen"));
        Assert.assertFalse(ueber.uebersetzen(522).equals("Starkregen"));
    }
    @Test
    public void caseThirtyOne(){
        Assert.assertTrue(ueber.uebersetzen(531).equals("Regenschauer"));
        Assert.assertFalse(ueber.uebersetzen(531).equals("Starkregen"));
    }
    @Test
    public void caseThirtyTow(){
        Assert.assertTrue(ueber.uebersetzen(600).equals("Leichter Schneefall"));
        Assert.assertFalse(ueber.uebersetzen(600).equals("Starkregen"));
    }
    @Test
    public void caseThirtyThree(){
        Assert.assertTrue(ueber.uebersetzen(601).equals("Schneefall"));
        Assert.assertFalse(ueber.uebersetzen(601).equals("Starkregen"));
    }
    @Test
    public void caseThirtyFour(){
        Assert.assertTrue(ueber.uebersetzen(602).equals("Starker Schneefall"));
        Assert.assertFalse(ueber.uebersetzen(602).equals("Starkregen"));
    }
    @Test
    public void caseThirtyFive(){
        Assert.assertTrue(ueber.uebersetzen(611).equals("Schneeregen"));
        Assert.assertFalse(ueber.uebersetzen(611).equals("Starkregen"));
    }
    @Test
    public void caseThirtySix(){
        Assert.assertTrue(ueber.uebersetzen(612).equals("Schneeregen"));
        Assert.assertFalse(ueber.uebersetzen(612).equals("Starkregen"));
    }
    @Test
    public void caseThirtySeven(){
        Assert.assertTrue(ueber.uebersetzen(615).equals("Leichter Regen, Schneemix"));
        Assert.assertFalse(ueber.uebersetzen(615).equals("Starkregen"));
    }
    @Test
    public void caseThirtyEight(){
        Assert.assertTrue(ueber.uebersetzen(616).equals("Regen, Schneemix"));
        Assert.assertFalse(ueber.uebersetzen(616).equals("Starkregen"));
    }
    @Test
    public void caseThirtyNine(){
        Assert.assertTrue(ueber.uebersetzen(620).equals("leichter Schneefall"));
        Assert.assertFalse(ueber.uebersetzen(620).equals("Starkregen"));
    }
    @Test
    public void caseFortyOne(){
        Assert.assertTrue(ueber.uebersetzen(621).equals("Schneefall"));
        Assert.assertFalse(ueber.uebersetzen(621).equals("Starkregen"));
    }
    @Test
    public void caseFortyTwo(){
        Assert.assertTrue(ueber.uebersetzen(622).equals("Starker Schneefall"));
        Assert.assertFalse(ueber.uebersetzen(622).equals("Starkregen"));
    }
    @Test
    public void caseFortyThree(){
        Assert.assertTrue(ueber.uebersetzen(701).equals("Nebel"));
        Assert.assertFalse(ueber.uebersetzen(701).equals("Starkregen"));
    }
    @Test
    public void caseSeventySeven(){
        Assert.assertTrue(ueber.uebersetzen(711).equals("Dunst"));
        Assert.assertFalse(ueber.uebersetzen(711).equals("Starkregen"));
    }
    @Test
    public void caseFortyFour(){
        Assert.assertTrue(ueber.uebersetzen(721).equals("Dunstschleier"));
        Assert.assertFalse(ueber.uebersetzen(721).equals("Starkregen"));
    }
    @Test
    public void caseFortyFive(){
        Assert.assertTrue(ueber.uebersetzen(731).equals("Staub, Sand"));
        Assert.assertFalse(ueber.uebersetzen(731).equals("Starkregen"));
    }
    @Test
    public void caseFortySix(){
        Assert.assertTrue(ueber.uebersetzen(741).equals("Nebel"));
        Assert.assertFalse(ueber.uebersetzen(741).equals("Starkregen"));
    }
    @Test
    public void caseFortySeven(){
        Assert.assertTrue(ueber.uebersetzen(751).equals("Sand"));
        Assert.assertFalse(ueber.uebersetzen(751).equals("Starkregen"));
    }
    @Test
    public void caseFortyEight(){
        Assert.assertTrue(ueber.uebersetzen(761).equals("Staub"));
        Assert.assertFalse(ueber.uebersetzen(761).equals("Starkregen"));
    }
    @Test
    public void caseFortyNine(){
        Assert.assertTrue(ueber.uebersetzen(762).equals("Vulkanasche"));
        Assert.assertFalse(ueber.uebersetzen(762).equals("Starkregen"));
    }
    @Test
    public void caseFifty(){
        Assert.assertTrue(ueber.uebersetzen(771).equals("Sturmböen"));
        Assert.assertFalse(ueber.uebersetzen(771).equals("Starkregen"));
    }
    @Test
    public void caseFiftyOne(){
        Assert.assertTrue(ueber.uebersetzen(781).equals("Tornado"));
        Assert.assertFalse(ueber.uebersetzen(781).equals("Starkregen"));
    }
    @Test
    public void caseFiftyTwo(){
        Assert.assertTrue(ueber.uebersetzen(800).equals("Klarer Himmel"));
        Assert.assertFalse(ueber.uebersetzen(800).equals("Starkregen"));
    }
    @Test
    public void caseFiftyThree(){
        Assert.assertTrue(ueber.uebersetzen(801).equals("Schleierwolken"));
        Assert.assertFalse(ueber.uebersetzen(801).equals("Starkregen"));
    }
    @Test
    public void caseFiftyFour(){
        Assert.assertTrue(ueber.uebersetzen(802).equals("auflockernd Bewölkt"));
        Assert.assertFalse(ueber.uebersetzen(802).equals("Starkregen"));
    }
    @Test
    public void caseFiftyFive(){
        Assert.assertTrue(ueber.uebersetzen(803).equals("abwechselnd Bewölkt"));
        Assert.assertFalse(ueber.uebersetzen(803).equals("Starkregen"));
    }
    @Test
    public void caseFiftySix(){
        Assert.assertTrue(ueber.uebersetzen(804).equals("Bewölkt"));
        Assert.assertFalse(ueber.uebersetzen(804).equals("Starkregen"));
    }
    @Test
    public void caseFiftySeven(){
        Assert.assertTrue(ueber.uebersetzen(900).equals("Tornado"));
        Assert.assertFalse(ueber.uebersetzen(900).equals("Starkregen"));
    }
    @Test
    public void caseFiftyEight(){
        Assert.assertTrue(ueber.uebersetzen(901).equals("Tropensturm"));
        Assert.assertFalse(ueber.uebersetzen(901).equals("Starkregen"));
    }
    @Test
    public void caseFiftyNine(){
        Assert.assertTrue(ueber.uebersetzen(902).equals("Hurrikan"));
        Assert.assertFalse(ueber.uebersetzen(902).equals("Starkregen"));
    }
    @Test
    public void caseSixty(){
        Assert.assertTrue(ueber.uebersetzen(903).equals("kalt"));
        Assert.assertFalse(ueber.uebersetzen(903).equals("Starkregen"));
    }
    @Test
    public void caseSixtyOne(){
        Assert.assertTrue(ueber.uebersetzen(904).equals("heiß"));
        Assert.assertFalse(ueber.uebersetzen(904).equals("Starkregen"));
    }
    @Test
    public void caseSixtyTwo(){
        Assert.assertTrue(ueber.uebersetzen(905).equals("windig"));
        Assert.assertFalse(ueber.uebersetzen(905).equals("Starkregen"));
    }
    @Test
    public void caseSixtyThree(){
        Assert.assertTrue(ueber.uebersetzen(906).equals("Hagel"));
        Assert.assertFalse(ueber.uebersetzen(906).equals("Starkregen"));
    }
    @Test
    public void caseSixtyFour(){
        Assert.assertTrue(ueber.uebersetzen(951).equals("windstill"));
        Assert.assertFalse(ueber.uebersetzen(951).equals("Starkregen"));
    }
    @Test
    public void caseSixtyFive(){
        Assert.assertTrue(ueber.uebersetzen(952).equals("leichte Briese"));
        Assert.assertFalse(ueber.uebersetzen(952).equals("Starkregen"));
    }
    @Test
    public void caseSixtySix(){
        Assert.assertTrue(ueber.uebersetzen(953).equals("sanfte Briese"));
        Assert.assertFalse(ueber.uebersetzen(953).equals("Starkregen"));
    }
    @Test
    public void caseSixtySeven(){
        Assert.assertTrue(ueber.uebersetzen(954).equals("moderate Briese"));
        Assert.assertFalse(ueber.uebersetzen(954).equals("Starkregen"));
    }
    @Test
    public void caseSixtyEight(){
        Assert.assertTrue(ueber.uebersetzen(955).equals("frische Briese"));
        Assert.assertFalse(ueber.uebersetzen(955).equals("Starkregen"));
    }
    @Test
    public void caseSixtyNine(){
        Assert.assertTrue(ueber.uebersetzen(956).equals("Starke Briese"));
        Assert.assertFalse(ueber.uebersetzen(956).equals("Starkregen"));
    }
    @Test
    public void caseSeventy(){
        Assert.assertTrue(ueber.uebersetzen(957).equals("starker Wind"));
        Assert.assertFalse(ueber.uebersetzen(957).equals("Starkregen"));
    }
    @Test
    public void caseSeventyOne(){
        Assert.assertTrue(ueber.uebersetzen(958).equals("leichter Wind"));
        Assert.assertFalse(ueber.uebersetzen(958).equals("Starkregen"));
    }
    @Test
    public void caseSeventyTwo(){
        Assert.assertTrue(ueber.uebersetzen(959).equals("Starkwind"));
        Assert.assertFalse(ueber.uebersetzen(959).equals("Starkregen"));
    }
    @Test
    public void caseSeventyThree(){
        Assert.assertTrue(ueber.uebersetzen(960).equals("Sturm"));
        Assert.assertFalse(ueber.uebersetzen(960).equals("Starkregen"));
    }
    @Test
    public void caseSeventyFour(){
        Assert.assertTrue(ueber.uebersetzen(961).equals("Starker Sturm"));
        Assert.assertFalse(ueber.uebersetzen(961).equals("Starkregen"));
    }
    @Test
    public void caseSeventyFive(){
        Assert.assertTrue(ueber.uebersetzen(962).equals("Hurrikan"));
        Assert.assertFalse(ueber.uebersetzen(962).equals("Starkregen"));
    }
}