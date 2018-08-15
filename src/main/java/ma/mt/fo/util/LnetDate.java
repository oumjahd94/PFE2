package ma.mt.fo.util;

import java.util.GregorianCalendar;

public class LnetDate
{ 
	
  public static int jour;
  public static int mois;
  public static int annee;
  
  public LnetDate()
  {
    GregorianCalendar laDate = new GregorianCalendar();
    
    jour = laDate.get(5);
    mois = laDate.get(2) + 1;
    annee = laDate.get(1);
  }
  
  public LnetDate(int j, int m, int a) {
    jour = j;
    mois = m;
    annee = a;
  }
  
  public LnetDate(String date) { 
	  
    int index1 = date.indexOf("/");
    int index2 = date.lastIndexOf("/");
    jour = Integer.parseInt(date.substring(0, index1));
    mois = Integer.parseInt(date.substring(index1 + 1, index2));
    annee = Integer.parseInt(date.substring(index2 + 1, date.length()));
  }
  
  public LnetDate(String j, String m, String a) {
    jour = Integer.parseInt(j);
    mois = Integer.parseInt(m);
    annee = Integer.parseInt(a);
  }
  
  public String RenvoieDate(String separateur) {
    String maDate = jour + separateur + mois + separateur + annee;
    return maDate;
  }
  
  public String RenvoieDateAAAAMMJJ(String separateur) {
    String maDate = annee + separateur + mois + separateur + jour;
    return maDate;
  }
  
  public static String RenvoieDateDuJourAAAAMMJJ(String separateur) {
    LnetDate myDate = new LnetDate();
    return myDate.RenvoieDateAAAAMMJJ(separateur);
  }
  
  public static String RenvoieDate(String separateur, int j, int m, int a) {
    String maDate = "";
    if (j == 1) {
      if ((m != 1) && (a != 1)) {
        maDate = maDate + jour;
      } else {
        maDate = maDate + jour + separateur;
      }
    }
    if (m == 1) {
      if (a != 1) {
        maDate = maDate + mois;
      } else {
        maDate = maDate + mois + separateur;
      }
    }
    if (a == 1) {
      maDate = maDate + annee;
    }
    return maDate;
  }
  
  public static String RenvoieDateMoisStr() {
    String monthName = "";
    switch (mois) {
    case 1: 
      monthName = "janvier";
      break;
    case 2: 
      monthName = "f�vrier";
      break;
    case 3: 
      monthName = "mars";
      break;
    case 4: 
      monthName = "avril";
      break;
    case 5: 
      monthName = "mai";
      break;
    case 6: 
      monthName = "juin";
      break;
    case 7: 
      monthName = "juillet";
      break;
    case 8: 
      monthName = "ao�t";
      break;
    case 9: 
      monthName = "septembre";
      break;
    case 10: 
      monthName = "octobre";
      break;
    case 11: 
      monthName = "novembre";
      break;
    case 12: 
      monthName = "d�cembre";
    }
    
    String maDate = jour + " " + monthName + " " + annee;
    return maDate;
  }
  
  public static String RenvoieDateMoisStr(int j, int m, int a) {
    String monthName = "";
    String maDate = "";
    switch (mois) {
    case 1: 
      monthName = "janvier";
      break;
    case 2: 
      monthName = "f�vrier";
      break;
    case 3: 
      monthName = "mars";
      break;
    case 4: 
      monthName = "avril";
      break;
    case 5: 
      monthName = "mai";
      break;
    case 6: 
      monthName = "juin";
      break;
    case 7: 
      monthName = "juillet";
      break;
    case 8: 
      monthName = "ao�t";
      break;
    case 9: 
      monthName = "septembre";
      break;
    case 10: 
      monthName = "octobre";
      break;
    case 11: 
      monthName = "novembre";
      break;
    case 12: 
      monthName = "d�cembre";
    }
    
    if (j == 1) {
      maDate = maDate + jour + " ";
    }
    if (m == 1) {
      maDate = maDate + monthName + " ";
    }
    if (a == 1) {
      maDate = maDate + annee;
    }
    return maDate;
  }
}
