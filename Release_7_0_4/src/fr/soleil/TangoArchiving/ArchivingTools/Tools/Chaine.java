package fr.soleil.TangoArchiving.ArchivingTools.Tools;


import java.util.ArrayList;

/**
 * Cette classe contient des methodes utiles pour manipuler les chaines de caractere.
 * <BR>
 * Elle contient :
 * <UL>
 *    <LI>Une methode {@link #substring(String, int, int) substring(...)} evoluee.
 *    <LI>Deux methodes {@link #lTrim(String)} et {@link #rTrim(String)}
 *        pour retirer les espaces a gauche ou a droite.
 *    <LI>Une methode {@link #remplace(String, String, String) remplace(...)}
 *        qui permet de remplacer une chaine dans une autre.
 *    <LI>Une methode {@link #separe(String, String)}
 *        qui permet de decouper une chaine en fonction d'un delimiteur.
 *    <LI>Deux methodes {@link #groupe(String[], String)} et {@link #groupe(String[])}
 *        qui permettent de regrouper des chaines separees.
 *    <LI>Une methode {@link #compte(String, String)} pour compter
 *        le nombre d'occurence d'une chaine dans une autre.
 *    <LI>Une methode {@link #repeter(String, int)} qui permet de creer une chaine composee
 *        de plusieurs occurences d'une autre chaine.
 *    <LI>Une methode {@link #retireAccents(String)} qui permet de retirer les caracteres accentues,
 *        et deux autre methodes qui permettent en plus de changer la casse des lettres
 *        ({@link #toUpperCaseSansAccents(String)} et {@link #toLowerCaseSansAccents(String)}).
 *    <LI>Plusieurs methodes qui permettent de convertir des nombre en hexadecimal
 *        ({@link #byteToHex(int)}, {@link #shortToHex(int)}, {@link #charToHex(char)}
 *        et {@link #intToHex(int)})
 * </UL>
 */
public class Chaine
{
        //----------  Constantes  ----------
    /*
     * Cette constante contient les caracteres accentues geres.
     * <BR>
     * Les equivalents sans accent de ces caracteres sont contenus dans la constante
     * {@link #CHAR_SANS_ACCENTS}
     */
    private static final String CHAR_ACCENTS      =   "\u00E9\u00E8\u00EA\u00EB\u00E0"
                                                    + "\u00E4\u00E2\u00EE\u00EF\u00F9"
                                                    + "\u00FB\u00FC\u00F4\u00F6\u00E7"
                                                    + "\u00C9\u00C8\u00CA\u00CB\u00C0"
                                                    + "\u00C4\u00C2\u00CE\u00CF\u00D9"
                                                    + "\u00DB\u00DC\u00D4\u00D6\u00C7";

    /*
     * Cette constante contient les caracteres sans accent correspondant a la chaine
     * {@link #CHAR_ACCENTS}.
     */
    private static final String CHAR_SANS_ACCENTS = "eeeeaaaiiuuuoocEEEEAAAIIUUUOOC";

    /*
     * Constante contenant les codes hexadecimaux de 0 a F.
     */
    private static final String HEXA = "0123456789ABCDEF";

        //----------  Methodes static  ----------
    /**
     * Retire tous les accents d'une chaine de caracteres.
     * @param      String chaine.
     * @return     Chaine de caractere sans accents.
     */
    public static String retireAccents(String chaine)
    {
        if (chaine == null)
        {
            return null;
        }
        
        // Remplacement des caracteres speciaux
        StringBuffer buffer = new StringBuffer(chaine);
        int longueur = buffer.length();
        
        for (int index = 0; index < longueur; index++)
        {
            char lettre = buffer.charAt(index);
            int indice = CHAR_ACCENTS.indexOf(lettre);
            
            if (indice >= 0)
            {
                buffer.setCharAt(index, CHAR_SANS_ACCENTS.charAt(indice));
            }
        }
        
        return buffer.toString();
    }
    
    /**
     * Convertie une chaine en majuscule en enlevant tous les accents.
     * @param      String chaine
     * @return     Chaine de caractere en majuscule
     */
    public static String toUpperCaseSansAccents(String chaine)
    {
        if (chaine == null)
        {
            return null;
        }
        
        return retireAccents(chaine).toUpperCase();
    }
    
    /**
     * Convertie une chaine en minuscule en enlevant tous les accents.
     * @param      String chaine
     * @return     Chaine de caractere en minuscule
     */
    public static String toLowerCaseSansAccents(String chaine)
    {
        if (chaine == null)
        {
            return null;
        }
        
        return retireAccents(chaine).toLowerCase();
    }
    
    /**
     * Extrait une chaine de caracteres en fonction de sa position et de sa longueur.
     * <BR>
     * Cette methode est relativement sophistiquee, car elle permet d'utiliser
     * une position et un indice negatifs.
     * @param chaine   Chaine de caracteres a partir de laquelle on extrait la nouvelle chaine.
     * @param position Position de depart de la chaine a extraire
     *                 (peut etre negatif ou superieur a la longueur de <CODE>chaine</CODE>)
     * @param longueur Longueur de la chaine a extraire
     *                 (peut etre negatif ou plus long que <CODE>chaine</CODE>)
     * @return Chaine extraite.
     */
    public static String substring(String chaine, int position, int longueur)
    {
        if (chaine == null)
        {
            return null;
        }

        int lgChaine = chaine.length();
        
        if (longueur < 0)
        {
            longueur = -longueur;
            position -= longueur;
        }
        
        if (position < 0)
        {
            position = -position;

            if (longueur < position)
            {
                return "";
            }
            longueur -= position;
            position = 0;
        }

        if (position >= lgChaine)
        {
            return "";
        }
        
        if ((position + longueur) >= lgChaine)
        {
            return chaine.substring(position);
        }
        
        return chaine.substring(position, position + longueur);
    }
    
    /**
     * Remplace toutes les occurences de <CODE>aRemplacer</CODE>
     * dans <CODE>chaine</CODE> par <CODE>remplacement</CODE>.
     * @param chaine       Chaine dans laquelle on effectue les remplacements.
     * @param aRemplacer   Chaine a remplacer.
     * @param remplacement Chaine de remplacement.
     * @return Chaine modifiee.
     */
    public static String remplace(String chaine, String aRemplacer, String remplacement)
    {
        int lgDelim = aRemplacer.length();
        ArrayList listeDelimiteurs = new ArrayList();
        StringBuffer chaineFinale;

        int debut;
        int indiceListe;
        
        // Recherche de la chaine a remplacer et memorisation de ses positions
        debut = 0;
        do
        {
            debut = chaine.indexOf(aRemplacer, debut);
            
            if (debut >= 0)
            {
                // Memorisation de la position de la chaine a remplacer
                listeDelimiteurs.add(new Integer(debut));
                debut += lgDelim;
            }
        } while (debut >= 0);
        
        // On verifie la presence de la chaine a remplacer
        if (listeDelimiteurs.size() == 0)
        {
            // Il n'y a rien a remplacer
            return chaine;
        }
        
        // Remplacement des parametres en commencant par la fin
        chaineFinale = new StringBuffer(chaine);
        indiceListe = listeDelimiteurs.size() - 1;
        
        do
        {
            // Lecture des positions des chaines a remplacer
            debut = ((Integer)listeDelimiteurs.get(indiceListe--)).intValue();
            
            // Remplacement dans la chaine
            chaineFinale.replace(debut, debut + lgDelim, remplacement);
        } while (indiceListe >= 0);
        
        return chaineFinale.toString();
    }
    
    /**
     * Separe les champs de <CODE>chaine</CODE> delimites par <CODE>separateur</CODE>
     * et les renvoie dans un tableau.
     * @param  chaine     Chaine de caractere a separer.
     * @param  separateur separateur des champs.
     * @return Tableau de <CODE>String</CODE> contenant les champs.
     */
    public static String[] separe(String chaine, String separateur)
    {
        int nbParties = compte(chaine, separateur) + 1;
        String[] parties = new String[nbParties];
        
        // Remplissage du tableau.
        int indice = 0;
        for (int i = 0; i < nbParties; i++)
        {
            int fin = chaine.indexOf(separateur, indice);
            if (fin >= 0)
            {
                parties[i] = chaine.substring(indice, fin);
            }
            else
            {
                parties[i] = chaine.substring(indice);
            }
            indice = fin + separateur.length();
        }

        return parties;
    }
    
    /**
     * Regroupe tous les elements d'un tableau de <CODE>String</CODE>
     * dans une chaine unique (sans ajout de separateur).
     * @param tableau Tableau contenant toutes les chaines a regrouper.
     * @return Chaine de regroupement.
     */
    public static String groupe(String[] tableau)
    {
        return groupe(tableau, "");
    }

    /**
     * Regroupe tous les elements d'un tableau de <CODE>String</CODE>
     * dans une chaine unique en les separant par <CODE>separateur</CODE>.
     * @param tableau    Tableau contenant toutes les chaines a regrouper.
     * @param separateur Utilise pour separer les differents elements du tableau.
     * @return Chaine de regroupement.
     */
    public static String groupe(String[] tableau, String separateur)
    {
        if (tableau == null)
        {
            return null;
        }
        
        if (tableau.length == 0)
        {
            return "";
        }

        if (separateur == null)
        {
            separateur = "";
        }

        // Calcul de la taille pour optimiser la performence du StringBuffer.
        int taille = 0;
        for (int i = 0; i < tableau.length; i++)
        {
            if (tableau[i] != null)
            {
                taille += tableau[i].length();
            }
        }
        taille += (tableau.length - 1) * separateur.length();

        // Regroupement du tableau dans le StringBuffer
        StringBuffer groupe = new StringBuffer(taille);

        for (int i = 0; i < tableau.length; i++)
        {
            if (tableau[i] != null)
            {
                groupe.append(tableau[i]);
            }
            if (i < (tableau.length - 1) )
            {
                groupe.append(separateur);
            }
        }
        return groupe.toString();
    }
    
    /**
     * Supprime les espaces a gauche de <CODE>chaine</CODE>.
     * @param chaine Chaine dont on souhaite retirer les espaces a gauche.
     * @return Chaine sans les espaces a gauche.
     */
    public static String lTrim(String chaine)
    {
        if (chaine == null)
        {
            return null;
        }
        
        int indice;
        for (indice = 0; indice < chaine.length(); indice++)
        {
            if (chaine.charAt(indice) != ' ')
            {
                break;
            }
        }

        if (indice == chaine.length())
        {
            return "";
        }
        
        if (indice > 0)
        {
            return chaine.substring(indice);
        }
        else
        {
            return chaine;
        }
    }
    
    /**
     * Supprime les zeros a gauche de <CODE>chaine</CODE>.
     * @param chaine Chaine dont on souhaite retirer les espaces a gauche.
     * @return Chaine sans les espaces a gauche.
     */
    public static String lTrimZero(String chaine)
    {
        if (chaine == null)
        {
            return null;
        }
        
        int indice;
        for (indice = 0; indice < chaine.length(); indice++)
        {
            if (chaine.charAt(indice) != '0')
            {
                break;
            }
        }

        if (indice == chaine.length())
        {
            return "";
        }
        
        if (indice > 0)
        {
            return chaine.substring(indice);
        }
        else
        {
            return chaine;
        }
    }
    
    /**
     * Supprime les espaces a droite de <CODE>chaine</CODE>.
     * @param chaine Chaine dont on souhaite retirer les espaces a droite.
     * @return Chaine sans les espaces a droite.
     */
    public static String rTrim(String chaine)
    {
        if (chaine == null)
        {
            return null;
        }
        
        int indice;
        for (indice = (chaine.length() - 1); indice >= 0; indice--)
        {
            if (chaine.charAt(indice) != ' ')
            {
                break;
            }
        }

        if (indice < 0)
        {
            return "";
        }
        
        indice++;
        
        if (indice < chaine.length())
        {
            return chaine.substring(0, indice);
        }
        else
        {
            return chaine;
        }
    }
    
    /**
     * Creation d'une chaine contenant <CODE>nombre</CODE> fois le motif <CODE>chaine</CODE>.
     * @param  chaine Motif a repeter.
     * @param  nombre Nombre de repetition du motif.
     * @return Chaine contenant <CODE>nombre</CODE> fois le motif <CODE>chaine</CODE>.
     */
    public static String repeter(String chaine, int nombre)
    {
        if (chaine == null)
        {
            return null;
        }
        
        if ( (chaine.equals("")) || (nombre <= 0) )
        {
            return "";
        }
        
        int longueur = chaine.length() * nombre;
        StringBuffer concat = new StringBuffer(longueur);
        
        for (int i = 0; i < nombre; i++)
        {
            concat.append(chaine);
        }
        return concat.toString();
    }
    
    /**
     * Compte le nombre d'occurences du motif <CODE>partie</CODE> dans <CODE>chaine</CODE>.
     * @param  chaine Chaine dans laquelle on souhaite compter le motif <CODE>partie</CODE>.
     * @param  partie Motif a compter dans <CODE>chaine</CODE>.
     * @return Nombre de motifs.
     */
    public static int compte(String chaine, String partie)
    {
        if (   (chaine == null)
            || (partie == null)
            || (chaine.equals(""))
            || (partie.equals(""))
           )
        {
            return 0;
        }
        
        int nb = 0;
        int indice = 0;
        while ( (indice = chaine.indexOf(partie, indice)) >= 0 )
        {
            indice += partie.length();
            nb++;
        }
        return nb;
    }

    /**
     * Convertion d'un <CODE>byte</CODE> en hexadecimal (resultat sur 8 bits).
     * <BR>
     * <B>Attention :</B> Seuls les 8 bits de poids faible sont convertis.
     *                    Les autres bits sont ignores.
     * @param nombre Nombre 8 bits a convertir en hexadecimal.
     * @return Chaine representant le nombre en hexadecimal.
     */
    public static String byteToHex(int nombre)
    {
        int upper;
        int lower;
        
        upper = (nombre & 0xF0) >>> 4;
        lower = (nombre & 0x0F);
        
        return "" + HEXA.charAt(upper) + HEXA.charAt(lower);
    }

    /**
     * Convertion d'un <CODE>short</CODE> en hexadecimal (resultat sur 16 bits).
     * <BR>
     * <B>Attention :</B> Seuls les 16 bits de poids faible sont convertis.
     *                    Les autres bits sont ignores.
     * @param nombre Nombre 16 bits a convertir en hexadecimal.
     * @return Chaine representant le nombre en hexadecimal.
     */
    public static String shortToHex(int nombre)
    {
        int upper;
        int lower;
        
        upper = (nombre & 0xFF00) >>> 8 ;
        lower = (nombre & 0x00FF);
        
        return byteToHex(upper) + byteToHex(lower);
    }

    /**
     * Convertion d'un caractere en hexadecimal (resultat sur 16 bits).
     * @param caractere Caractere a convertir en hexadecimal.
     * @return Chaine representant le nombre en hexadecimal.
     */
    public static String charToHex(char caractere)
    {
        return shortToHex(caractere);
    }

    /**
     * Convertion d'un <CODE>int</CODE> en hexadecimal (resultat sur 32 bits).
     * @param nombre Nombre 32 bits a convertir en hexadecimal.
     * @return Chaine representant le nombre en hexadecimal.
     */
    public static String intToHex(int nombre)
    {
        int upper;
        int lower;
        
        upper = (nombre & 0xFFFF0000) >>> 16;
        lower = (nombre & 0x0000FFFF);
        
        return shortToHex(upper) + shortToHex(lower);
    }

        //----------  Constructeur  ----------
    /*
     * Constructeur declare <CODE>private</CODE> pour empecher l'instanciation de la classe.
     */
    private Chaine()
    {
    }
}
