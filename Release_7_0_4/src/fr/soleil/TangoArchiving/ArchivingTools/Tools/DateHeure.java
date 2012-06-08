package fr.soleil.TangoArchiving.ArchivingTools.Tools;


import java.util.*;
import java.text.*;


/**
 * Gestion de la date et de l'heure.
 * <BR>
 * Les classes java qui gerent la date et l'heure ne sont pas pratique,
 * et ne possedent pas de methode permettant de compter l'ecart entre 2 dates.
 * <BR>
 * <BR>
 * Lors de la construction d'un objet <CODE>DateHeure</CODE>,
 * la date passee en parametre est automatiquement corrigee.<BR>
 * Par exemple, si ont construit la date <CODE>32/05/2002</CODE>,
 * elle sera convertie en <CODE>01/06/2002</CODE>.<BR>
 * Afin de pourvoir tester la validite d'une date, la methode {@link #wasValid()}
 * indique si la date passee au constructeur etait correct ou pas.
 * <BR>
 * Cette classe possede les elements suivants :
 * <UL>
 * <LI>Des constantes utiles pour formatter une date : {@link #FORMAT_DATE_TEXT},
 * {@link #FORMAT_DATE_NORMAL}, {@link #FORMAT_HEURE_NORMAL}, {@link #FORMAT_DATE_HEURE_NORMAL},
 * {@link #FORMAT_DATE_HEURE_MILLI_NORMAL}, {@link #FORMAT_DATE_SQL},
 * {@link #FORMAT_DATE_DB2} et {@link #FORMAT_DATE_HEURE_XML}.
 * <LI>Des constantes pour chaque jour de la semaine
 * (1 correspond a lundi et non pas a dimanche comme dans le systeme anglo-saxon) :
 * {@link #LUNDI}, {@link #MARDI}, {@link #MERCREDI}, {@link #JEUDI},
 * {@link #VENDREDI}, {@link #SAMEDI} et {@link #DIMANCHE}.
 * <LI>Des constantes pour chaque mois (de 1 a 12) :
 * {@link #JANVIER}, {@link #FEVRIER}, {@link #MARS}, {@link #AVRIL}, {@link #MAI}, {@link #JUIN},
 * {@link #JUILLET}, {@link #AOUT}, {@link #SEPTEMBRE}, {@link #OCTOBRE},
 * {@link #NOVEMBRE} et {@link #DECEMBRE}.
 * <LI>Differents constructeurs pour pouvoir creer des dates a partir de nombreux formats.
 * <LI>Une methode {@link #wasValid()} pour savoir si la date passee au constructeur etait correct.
 * <LI>Des methodes de convertion pour convertir l'objet <CODE>DateHeure</CODE>
 * en <CODE>Date</CODE> ({@link #toDate()}),
 * en <CODE>String</CODE> ({@link #toString()} et {@link #toString(java.lang.String)}) et
 * pour faire une copie de l'objet <CODE>DateHeure</CODE> ({@link #clone()}).
 * <LI>Deux methodes pour savoir si une annee est bissextile
 * ({@link #isBissextile()} et {@link #isBissextile(int)}).
 * <LI>Des methodes pour lire les donnees de la date et de l'heure ({@link #getAnnee()},
 * {@link #getMois()}, {@link #getJour()}, {@link #getJourSemaine()}, {@link #getJourAnnee()},
 * {@link #getHeure()}, {@link #getMinute()}, {@link #getSeconde()} et {@link #getMilliseconde()}).
 * <LI>Des methodes pour modifier les donnees de la date et de l'heure ({@link #addAnnee(int)},
 * {@link #addMois(int)}, {@link #addJour(int)}, {@link #addHeure(int)}, {@link #addMinute(int)},
 * {@link #addSeconde(int)} et {@link #addMilliseconde(int)}).
 * <LI>Des methodes pour comparer deux dates ({@link #compareTo(java.lang.Object)} et
 * {@link #equals(java.lang.Object)}).
 * <LI>Des methodes pour calculer l'ecart entre deux dates ({@link #anneesEntre(DateHeure)},
 * {@link #moisEntre(DateHeure)}, {@link #joursEntre(DateHeure)},
 * {@link #heuresEntre(DateHeure)}, {@link #minutesEntre(DateHeure)},
 * {@link #secondesEntre(DateHeure)} et {@link #millisecondesEntre(DateHeure)}).
 * </UL>
 */
public class DateHeure implements Cloneable , Comparable
{
    //----------  Constantes : formats des date et heures  ----------
    /**
     * Format text : <CODE>"jourSemaineLettre jourMois moisLettre annee"</CODE>.
     */
    public static final String FORMAT_DATE_TEXT = "EEEE dd MMMM yyyy";

    /**
     * Format text : <CODE>"dd/mm/yyyy"</CODE>.
     */
    public static final String FORMAT_DATE_NORMAL = "dd/MM/yyyy";

    /**
     * Format text : <CODE>"hh:mm:ss"</CODE>.
     */
    public static final String FORMAT_HEURE_NORMAL = "HH:mm:ss";

    /**
     * Format text : <CODE>"dd/mm/yyyy hh:mm:ss"</CODE>.
     */
    public static final String FORMAT_DATE_HEURE_NORMAL = "dd/MM/yyyy HH:mm:ss";

    /**
     * Format text : <CODE>"dd/mm/yyyy hh:mm:ss:mmm"</CODE>.
     */
    public static final String FORMAT_DATE_HEURE_MILLI_NORMAL = "dd/MM/yyyy HH:mm:ss:SSS";

    /**
     * Format text : <CODE>"yyyy-mm-dd"</CODE>.
     */
    public static final String FORMAT_DATE_SQL = "yyyy-MM-dd";

    public static final String FORMAT_DATE_HEURE_SQL = "yyyy-MM-dd HH:mm:ss";
    
    public static final String FORMAT_DATE_HEURE_MANAGER = "dd-MM-yyyy HH:mm:ss.SS";

    /**
     * Format text : <CODE>"yyyymmdd"</CODE>.
     */
    public static final String FORMAT_DATE_DB2 = "yyyyMMdd";

    public static final String FORMAT_DATE_MYSQL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_ORACLE = "dd-MM-yyyy HH:mm:ss";
    
    /**
     * Format text : <CODE>"yyyy-mm-ddThh:mm:ss"</CODE>.
     */
    public static final String FORMAT_DATE_HEURE_XML = "yyyy-MM-dd'T'HH:mm:ss";

    //----------  Constantes : jours de la semaine  ----------
    /**
     * Constante de jour de la semaine : lundi = 1.
     */
    public static final int LUNDI = 1;

    /**
     * Constante de jour de la semaine : mardi = 2.
     */
    public static final int MARDI = 2;

    /**
     * Constante de jour de la semaine : mercredi = 3.
     */
    public static final int MERCREDI = 3;

    /**
     * Constante de jour de la semaine : jeudi = 4.
     */
    public static final int JEUDI = 4;

    /**
     * Constante de jour de la semaine : vendredi = 5.
     */
    public static final int VENDREDI = 5;

    /**
     * Constante de jour de la semaine : samedi = 6.
     */
    public static final int SAMEDI = 6;

    /**
     * Constante de jour de la semaine : dimanche = 7.
     */
    public static final int DIMANCHE = 7;

    /**
     * Constante de mois : janvier = 1.
     */
    public static final int JANVIER = 1;

    /**
     * Constante de mois : fevrier = 2.
     */
    public static final int FEVRIER = 2;

    /**
     * Constante de mois : mars = 3.
     */
    public static final int MARS = 3;

    /**
     * Constante de mois : avril = 4.
     */
    public static final int AVRIL = 4;

    /**
     * Constante de mois : mai = 5.
     */
    public static final int MAI = 5;

    /**
     * Constante de mois : juin = 6.
     */
    public static final int JUIN = 6;

    /**
     * Constante de mois : juillet = 7.
     */
    public static final int JUILLET = 7;

    /**
     * Constante de mois : aout = 8.
     */
    public static final int AOUT = 8;

    /**
     * Constante de mois : septembre = 9.
     */
    public static final int SEPTEMBRE = 9;

    /**
     * Constante de mois : octobre = 10.
     */
    public static final int OCTOBRE = 10;

    /**
     * Constante de mois : novembre = 11.
     */
    public static final int NOVEMBRE = 11;

    /**
     * Constante de mois : decembre = 12.
     */
    public static final int DECEMBRE = 12;

    //----------  Attributs de classe  ----------
/*
     * Nombre de jours par mois (fevrier est compte comme 28 jours).
     */
    private static final int[] JOURS_MOIS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    //----------  Attributs d'instance  ----------
/* Indique l'annee. */
    private int annee;

    /* Indique le mois. */
    private int mois;

    /* Indique le jour. */
    private int jour;

    /* Indique l'heure. */
    private int heure;

    /* Indique les minutes. */
    private int minute;

    /* Indique les secondes. */
    private int seconde;

    /* Indique les millisecondes. */
    private int milli;

    /*
     * Indique si la date etait valide lors de la construction de l'objet
     * (Elle est corrigee automatiquement par les constructeurs).
     */
    private boolean dateWasValide;

    //----------  Constructeurs  ----------
    /**
     * Construit un objet <CODE>DateHeure</CODE> a la date et a l'heure courante.
     */
    public DateHeure ()
    {
        this( new Date() );
    }

    /**
     * Construit un objet <CODE>DateHeure</CODE> a partir d'un objet <CODE>java.util.Date</CODE>.
     *
     * @param Objet <CODE>java.util.Date</CODE> utilise pour la construction de l'objet.
     */
    public DateHeure ( Date date )
    {
        dateToNumber( date , this );
        dateWasValide = true;
    }

    /**
     * Construit un objet <CODE>DateHeure</CODE> a partir de la chaine passe en parametre.
     * <BR>
     * <CODE>format</CODE> est l'une des constantes <CODE>FORMAT_XXX</CODE> de cette classe,
     * ou un format personnalise (Voir la classe <CODE>java.text.SimpleDateFormat</CODE>).
     *
     * @param date   Chaine de caractere representant une date et/ou une heure.
     * @param format Format de la date.
     * @throws ParseException Si la chaine ne peut pas etre convertie.
     * @see #FORMAT_DATE_TEXT
     * @see #FORMAT_DATE_NORMAL
     * @see #FORMAT_HEURE_NORMAL
     * @see #FORMAT_DATE_HEURE_NORMAL
     * @see #FORMAT_DATE_HEURE_MILLI_NORMAL
     * @see #FORMAT_DATE_SQL
     * @see #FORMAT_DATE_DB2
     * @see #FORMAT_DATE_HEURE_XML
     */
    public DateHeure ( String date , String format )
            throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat( format );
        Date dateObj = dateFormat.parse( date );

        // Test de la validite de la date
        dateFormat.setLenient( false );
        try
        {
            dateFormat.parse( date );
            dateWasValide = true;
        }
        catch ( ParseException pEx )
        {
            dateWasValide = false;
        }

        // Initialisation des attributs
        dateToNumber( dateObj , this );
    }

    /**
     * Construit un objet <CODE>DateHeure</CODE> a partir de la date passee en parametre
     * (l'heure est initialisee a 0).
     * <BR>
     * Si la date passee en parametre est incorrect, elle est automatiquement corrigee.<BR>
     * Pour savoir si elle etait correct avant d'etre corrigee, appellez la methode {@link #wasValid()}.
     *
     * @param annee Indique l'annee.
     * @param mois  Indique le mois.
     * @param jour  Indique le jour.
     */
    public DateHeure ( int annee , int mois , int jour )
    {
        this( annee , mois , jour , 0 , 0 , 0 , 0 );
    }

    /**
     * Construit un objet <CODE>DateHeure</CODE> a partir de la date
     * et de l'heure passees en parametre (les millisecondes sont initialisee a 0).
     * <BR>
     * Si la date passee en parametre est incorrect, elle est automatiquement corrigee.<BR>
     * Pour savoir si elle etait correct avant d'etre corrigee, appellez la methode {@link #wasValid()}.
     *
     * @param annee   Indique l'annee.
     * @param mois    Indique le mois.
     * @param jour    Indique le jour.
     * @param heure   Indique l'heure.
     * @param minute  Indique les minutes.
     * @param seconde Indique les secondes.
     */
    public DateHeure ( int annee , int mois , int jour , int heure , int minute , int seconde )
    {
        this( annee , mois , jour , heure , minute , seconde , 0 );
    }

    /**
     * Construit un objet <CODE>DateHeure</CODE> a partir de la date,
     * de l'heure et des millisecondes passees en parametre.
     * <BR>
     * Si la date passee en parametre est incorrect, elle est automatiquement corrigee.<BR>
     * Pour savoir si elle etait correct avant d'etre corrigee, appellez la methode {@link #wasValid()}.
     *
     * @param annee        Indique l'annee.
     * @param mois         Indique le mois.
     * @param jour         Indique le jour.
     * @param heure        Indique l'heure.
     * @param minute       Indique les minutes.
     * @param seconde      Indique les secondes.
     * @param milliseconde Indique les millisecondes.
     */
    public DateHeure ( int annee , int mois , int jour , int heure , int minute , int seconde , int milliseconde )
    {
        GregorianCalendar calend = new GregorianCalendar( annee , mois - 1 , jour , heure , minute , seconde );
        Date date = new Date( calend.getTime().getTime() + milliseconde );

        dateToNumber( date , this );

        dateWasValide = ( this.annee == annee )
                        && ( this.mois == mois )
                        && ( this.jour == jour )
                        && ( this.heure == heure )
                        && ( this.minute == minute )
                        && ( this.seconde == seconde )
                        && ( this.milli == milliseconde );
    }

    //----------  Methodes statiques  ----------
/*
     * Decompose un objet Date en annee, mois, jour, heure, minute, seconde et millisecondes.
     * @param date Date a decomposer.
     */
    private static void dateToNumber ( Date date , DateHeure obj )
    {
        long timeMilli = date.getTime();

        GregorianCalendar calend = new GregorianCalendar();
        calend.setTime( date );

        obj.annee = calend.get( Calendar.YEAR );
        obj.mois = calend.get( Calendar.MONTH ) + 1;
        obj.jour = calend.get( Calendar.DAY_OF_MONTH );
        obj.heure = calend.get( Calendar.HOUR_OF_DAY );
        obj.minute = calend.get( Calendar.MINUTE );
        obj.seconde = calend.get( Calendar.SECOND );

        calend = new GregorianCalendar( obj.annee , obj.mois - 1 , obj.jour ,
                                        obj.heure , obj.minute , obj.seconde );

        obj.milli = ( int ) ( timeMilli - calend.getTime().getTime() );
    }

    /*
     * Convertie une date incorrect en date du calendrier.
     * @param dateHeure Date a corriger.
     */
    private static void corrigeDateHeure ( DateHeure dateHeure )
    {
        GregorianCalendar calend = new GregorianCalendar( dateHeure.annee , dateHeure.mois - 1 ,
                                                          dateHeure.jour , dateHeure.heure ,
                                                          dateHeure.minute , dateHeure.seconde );
        Date date = new Date( calend.getTime().getTime() + dateHeure.milli );

        dateToNumber( date , dateHeure );
    }

    /**
     * Indique si l'annee passee en parametre est d'une annee bissextile.
     *
     * @param annee Annee dont on souhaite savoir si elle est bissextile ou non.
     * @return <CODE>true</CODE>, si l'annee est bissextile.
     */
    public static boolean isBissextile ( int annee )
    {
        if ( ( annee % 4 ) == 0 )
        {
            if ( ( ( annee % 100 ) == 0 ) && ( ( annee % 400 ) != 0 ) )
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    //----------  Methodes d'instance  ----------
    /**
     * Indique si la date passee a la construction de la classe etait correcte.
     * <BR>
     * <B>Remarque :</B> Quelle que soit la valeur renvoyee par cette methode, la date interne est obligatoirement
     * correct car elle corrigee par le constructeur.
     *
     * @return true si la date passee au constructeur etait correct.
     */
    public boolean wasValid ()
    {
        return dateWasValide;
    }

    /**
     * Indique si la date est d'une annee bissextile.
     *
     * @return <CODE>true</CODE>, si l'annee est bissextile.
     */
    public boolean isBissextile ()
    {
        return isBissextile( annee );
    }

    /**
     * Convertion de la date et de l'heure en objet <CODE>java.util.Date</CODE>.
     *
     * @return Objet Date de meme date.
     */
    public Date toDate ()
    {
        GregorianCalendar calend = new GregorianCalendar( annee , mois - 1 , jour , heure , minute , seconde );
        return new Date( calend.getTime().getTime() + milli );
    }

    /**
     * Renvoie une chaine representant la date et l'heure.
     * <BR>
     * Le format utilise est {@link #FORMAT_DATE_HEURE_MILLI_NORMAL}.
     *
     * @return Une chaine representant la date et l'heure.
     */
    public String toString ()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat( FORMAT_DATE_HEURE_MILLI_NORMAL );
        return dateFormat.format( toDate() );
    }

    /**
     * Renvoie une chaine representant la date et l'heure en utilisant le format specifie.
     * <BR>
     * <CODE>format</CODE> est l'une des constantes <CODE>FORMAT_XXX</CODE> de cette classe,
     * ou un format personnalise (Voir la classe <CODE>java.text.SimpleDateFormat</CODE>).
     *
     * @param format Format de la date.
     * @return Une chaine representant la date et l'heure.
     * @see #FORMAT_DATE_TEXT
     * @see #FORMAT_DATE_NORMAL
     * @see #FORMAT_HEURE_NORMAL
     * @see #FORMAT_DATE_HEURE_NORMAL
     * @see #FORMAT_DATE_HEURE_MILLI_NORMAL
     * @see #FORMAT_DATE_SQL
     * @see #FORMAT_DATE_DB2
     * @see #FORMAT_DATE_HEURE_XML
     */
    public String toString ( String format )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat( format );
        return dateFormat.format( toDate() );
    }

/*  // Methode suprimee, car Java ne gere pas les semaines comme le calendrier Francais.
    // Il faudra faire une recherche du fonctionnement reel du calendrier pour re-developper cette methode.
    public int getSemaine()
    {
        GregorianCalendar calend = new GregorianCalendar(annee, mois - 1, jour, heure, minute, seconde);
        return calend.get(Calendar.WEEK_OF_YEAR);
    }
*/
    
    /**
     * Renvoie le numero du jour de la semaine.
     * <BR>
     * 1 pour lundi, 2 pour mardi, ..., 7 pour dimanche.<BR>
     * Il est possible d'utiliser les constantes :
     * <UL>
     * <LI>{@link #LUNDI}
     * <LI>{@link #MARDI}
     * <LI>{@link #MERCREDI}
     * <LI>{@link #JEUDI}
     * <LI>{@link #VENDREDI}
     * <LI>{@link #SAMEDI}
     * <LI>{@link #DIMANCHE}
     * </UL>
     *
     * @return Numero du jour de la semaine.
     */
    public int getJourSemaine ()
    {
        GregorianCalendar calend = new GregorianCalendar( annee , mois - 1 , jour , heure , minute , seconde );
        int jourSemaine = calend.get( Calendar.DAY_OF_WEEK ) - 1;
        if ( jourSemaine == 0 )
        {
            jourSemaine = 7;
        }

        return jourSemaine;
    }

    /**
     * Renvoie le jour de l'annee.
     *
     * @return Le jour de l'annee.
     */
    public int getJourAnnee ()
    {
        GregorianCalendar calend = new GregorianCalendar( annee , mois - 1 , jour , heure , minute , seconde );
        return calend.get( Calendar.DAY_OF_YEAR );
    }

    /**
     * Renvoie le nombre d'annees (signe) qui separent la date de <CODE>autreDateHeure</CODE>.
     * Le resultat renvoye par a.anneesEntre(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> negatif si a < b
     * <LI> positif si a > b
     * </UL>
     * <BR>
     * Remarque : Cette methode ne tient pas compte de l'heure.
     *
     * @param autreDateHeure Objet <CODE>DateHeure</CODE> dont on soustrait l'objet courant.
     * @return le nombre d'annees d'ecart (signe).
     */
    public int anneesEntre ( DateHeure autreDateHeure )
    {
        return ( int ) ( moisEntre( autreDateHeure ) / 12 );
    }

    /**
     * Renvoie le nombre de mois (signe) qui separent la date de <CODE>autreDateHeure</CODE>.
     * Le resultat renvoye par a.moisEntre(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> negatif si a < b
     * <LI> positif si a > b
     * </UL>
     * <BR>
     * Remarque : Cette methode ne tient pas compte de l'heure.
     *
     * @param autreDateHeure Objet <CODE>DateHeure</CODE> dont on soustrait l'objet courant.
     * @return le nombre de mois d'ecart (signe).
     */
    public int moisEntre ( DateHeure autreDateHeure )
    {
        int nbMois = 0;

        boolean positif;
        DateHeure grand;
        DateHeure petit;

        // Determination du signe.
        positif = ( millisecondesEntre( autreDateHeure ) >= 0 );

        // Recherche de l'ordre des dates a comparer.
        if ( positif )
        {
            grand = ( DateHeure ) this.clone();
            petit = ( DateHeure ) autreDateHeure.clone();
        }
        else
        {
            grand = ( DateHeure ) autreDateHeure.clone();
            petit = ( DateHeure ) this.clone();
        }

        // Test sur les jours pour eviter de compter un mois incomplet
        int nbJourMax = JOURS_MOIS[ grand.mois - 1 ];

        if ( ( grand.mois == 2 ) && grand.isBissextile() )
        {
            // Cas particulier des annees bissextiles
            nbJourMax = 29;
        }

        if ( ( petit.jour > grand.jour ) && ( grand.jour < nbJourMax ) )
        {
            // Le mois n'est pas complet, donc on saute un mois
            if ( petit.mois < 12 )
            {
                petit.mois++;
            }
            else
            {
                // Nous etions en fin d'annee, donc on passe a l'annee suivante
                petit.mois = 1;
                petit.annee++;
            }
        }

        // Test sur les mois pour eviter de compter les annees incompletes
        if ( petit.mois > grand.mois )
        {
            // L'annee n'est pas complete,
            // donc on comptabilise les mois jusqu'a le fin de l'annee
            // et on passe a l'annee suivante.
            nbMois = 13 - petit.mois;

            petit.mois = 1;
            petit.annee++;
        }

        // On compte ensuite l'ecart entre les mois
        nbMois += grand.mois - petit.mois;

        // On termine pas le nombre d'annee completes
        nbMois += ( grand.annee - petit.annee ) * 12;

        return positif ? nbMois : -nbMois;
    }

    /**
     * Renvoie le nombre de jours (signe) qui separent la date de <CODE>autreDateHeure</CODE>.
     * Le resultat renvoye par a.joursEntre(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> negatif si a < b
     * <LI> positif si a > b
     * </UL>
     *
     * @param autreDateHeure Objet <CODE>DateHeure</CODE> dont on soustrait l'objet courant.
     * @return le nombre de jours d'ecart (signe).
     */
    public int joursEntre ( DateHeure autreDateHeure )
    {
        // IMPORTANT :
        //     On ne peut pas diviser le nombre d'heures par 24 pour obtenir le nombre d'heures,
        //     a cause des changements d'heure (ete / hivers).
        //
        //     La methode utilisee ici, consiste a changer de fuseau horaire (sans changement d'heure).
        //

        GregorianCalendar calend1 = new GregorianCalendar( this.annee , this.mois - 1 ,
                                                           this.jour , this.heure ,
                                                           this.minute , this.seconde );
        GregorianCalendar calend2 = new GregorianCalendar( autreDateHeure.annee , autreDateHeure.mois - 1 ,
                                                           autreDateHeure.jour , autreDateHeure.heure ,
                                                           autreDateHeure.minute , autreDateHeure.seconde );
        TimeZone zonePST = new SimpleTimeZone( 0 , "PST" );

        calend1.setTimeZone( zonePST );
        calend2.setTimeZone( zonePST );

        long milliThis = calend1.getTime().getTime() + this.milli;
        long milliAutre = calend2.getTime().getTime() + autreDateHeure.milli;

        return ( int ) ( ( milliThis - milliAutre ) / 86400000L );
    }

    /**
     * Renvoie le nombre d'heures (signe) qui separent la date de <CODE>autreDateHeure</CODE>.
     * Le resultat renvoye par a.heuresEntre(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> negatif si a < b
     * <LI> positif si a > b
     * </UL>
     *
     * @param autreDateHeure Objet <CODE>DateHeure</CODE> dont on soustrait l'objet courant.
     * @return le nombre d'heures d'ecart (signe).
     */
    public long heuresEntre ( DateHeure autreDateHeure )
    {
        return minutesEntre( autreDateHeure ) / 60;
    }

    /**
     * Renvoie le nombre de minutes (signe) qui separent la date de <CODE>autreDateHeure</CODE>.
     * Le resultat renvoye par a.minutesEntre(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> negatif si a < b
     * <LI> positif si a > b
     * </UL>
     *
     * @param autreDateHeure Objet <CODE>DateHeure</CODE> dont on soustrait l'objet courant.
     * @return le nombre de minutes d'ecart (signe).
     */
    public long minutesEntre ( DateHeure autreDateHeure )
    {
        return secondesEntre( autreDateHeure ) / 60;
    }

    /**
     * Renvoie le nombre de secondes (signe) qui separent la date de <CODE>autreDateHeure</CODE>.
     * Le resultat renvoye par a.secondesEntre(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> negatif si a < b
     * <LI> positif si a > b
     * </UL>
     *
     * @param autreDateHeure Objet <CODE>DateHeure</CODE> dont on soustrait l'objet courant.
     * @return le nombre de secondes d'ecart (signe).
     */
    public long secondesEntre ( DateHeure autreDateHeure )
    {
        return millisecondesEntre( autreDateHeure ) / 1000;
    }

    /**
     * Renvoie le nombre de millisecondes (signe) qui separent la date de <CODE>autreDateHeure</CODE>.
     * Le resultat renvoye par a.millisecondesEntre(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> negatif si a < b
     * <LI> positif si a > b
     * </UL>
     *
     * @param autreDateHeure Objet <CODE>DateHeure</CODE> dont on soustrait l'objet courant.
     * @return le nombre de millisecondes d'ecart (signe).
     */
    public long millisecondesEntre ( DateHeure autreDateHeure )
    {
        long milliThis = toDate().getTime();
        long milliAutre = autreDateHeure.toDate().getTime();

        return milliThis - milliAutre;
    }

    /**
     * Lecture de l'annee.
     *
     * @return L'annee.
     */
    public int getAnnee ()
    {
        return annee;
    }

    /**
     * Lecture du mois.
     *
     * @return Le mois
     */
    public int getMois ()
    {
        return mois;
    }

    /**
     * Lecture du jour.
     *
     * @return Le jour.
     */
    public int getJour ()
    {
        return jour;
    }

    /**
     * Lecture de l'heure.
     *
     * @return L'heure
     */
    public int getHeure ()
    {
        return heure;
    }

    /**
     * Lecture des minutes.
     *
     * @return Les minutes.
     */
    public int getMinute ()
    {
        return minute;
    }

    /**
     * Lecture des secondes.
     *
     * @return Les secondes.
     */
    public int getSeconde ()
    {
        return seconde;
    }

    /**
     * Lecture des millisecondes.
     *
     * @return Les millisecondes
     */
    public int getMilliseconde ()
    {
        return milli;
    }

    /**
     * Ajoute <CODE>annee</CODE> annees.
     *
     * @param annee Nombre d'annees (signe) a ajouter.
     */
    public void addAnnee ( int annee )
    {
        this.annee += annee;
        corrigeDateHeure( this );
    }

    /**
     * Ajoute <CODE>mois</CODE> mois.
     *
     * @param mois Nombre de mois (signe) a ajouter.
     */
    public void addMois ( int mois )
    {
        this.mois += mois;
        corrigeDateHeure( this );
    }

    /**
     * Ajoute <CODE>jour</CODE> jours.
     *
     * @param jour Nombre de jours (signe) a ajouter.
     */
    public void addJour ( int jour )
    {
        this.jour += jour;
        corrigeDateHeure( this );
    }

    /**
     * Ajoute <CODE>heure</CODE> heures.
     *
     * @param heure Nombre d'heures (signe) a ajouter.
     */
    public void addHeure ( int heure )
    {
        this.heure += heure;
        corrigeDateHeure( this );
    }

    /**
     * Ajoute <CODE>minute</CODE> minutes.
     *
     * @param minute Nombre d'minute (signe) a ajouter.
     */
    public void addMinute ( int minute )
    {
        this.minute += minute;
        corrigeDateHeure( this );
    }

    /**
     * Ajoute <CODE>seconde</CODE> secondes.
     *
     * @param seconde Nombre de secondes (signe) a ajouter.
     */
    public void addSeconde ( int seconde )
    {
        this.seconde += seconde;
        corrigeDateHeure( this );
    }

    /**
     * Ajoute <CODE>milliseconde</CODE> millisecondes.
     *
     * @param milliseconde Nombre de millisecondes (signe) a ajouter.
     */
    public void addMilliseconde ( int milliseconde )
    {
        this.milli += milliseconde;
        corrigeDateHeure( this );
    }

    //----------  Implementation de l'interface Comparable  ----------
    /**
     * Compare 2 objets <CODE>DateHeure</CODE>.
     * Le resultat renvoye par a.compareTo(b) est :
     * <UL>
     * <LI> 0 si a == b
     * <LI> -1 si a < b
     * <LI> +1 si a > b
     * </UL>
     *
     * @param o Objet a comparer.
     * @return L'ordre des objets.
     * @throws <CODE>NullPointerException</CODE>
     *          si l'objet <CODE>o</CODE> est <CODE>null</CODE>.
     * @throws <CODE>ClassCastException</CODE>
     *          si l'objet <CODE>o</CODE>
     *          n'est pas un instance de <CODE>DateHeure</CODE>.
     */
    public int compareTo ( Object o )
    {
        if ( o == null )
        {
            throw new NullPointerException();
        }

        DateHeure autreDateHeure = ( DateHeure ) o;

        long ecart = millisecondesEntre( autreDateHeure );

        if ( ecart < 0 )
        {
            return -1;
        }

        if ( ecart > 0 )
        {
            return 1;
        }

        return 0;
    }

    //----------  Surcharge de la classe Object  ----------
    /**
     * Indique si 2 objets <CODE>DateHeure</CODE> sont egaux.
     * Cette methode compare toutes les donnees (date, heure et millisecondes).
     *
     * @param obj Objet <CODE>DateHeure</CODE> a comparer.
     */
    public boolean equals ( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }

        if ( obj.getClass() != DateHeure.class )
        {
            return false;
        }

        if ( millisecondesEntre( ( DateHeure ) obj ) == 0 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Cette methode surcharge celle de la classe <CODE>Object</CODE>.
     *
     * @return Une copie de cet objet.
     */
    public Object clone ()
    {
        try
        {
            return super.clone();
        }
        catch ( CloneNotSupportedException e )
        {
            // Ne peut pas se produire, car la classe implemente Cloneable.
            return null;
        }
    }
}
