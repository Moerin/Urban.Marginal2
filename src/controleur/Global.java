package controleur;

public interface Global {
	
	//--- constantes de connexion ---
	public static final int PORT = 6666 ;

	//--- d�placement, action ---
	public static final int
		GAUCHE = 0,
		DROITE = 1,
		HAUT = 2,
		BAS = 3,
		TIRE = 4 ;
	
	//--- �tat du personnage ---
	public static final String
		MARCHE = "marche",
		BLESSE = "touche",
		MORT = "mort" ;
	
	//--- les nombres ---
	public static final int
		NBPERSOS = 3,		// nombre de personnages diff�rents
		NBMURS = 20, 		// nombre de murs dans l'ar�ne
		LEPAS = 10,			// nombre de pixels de d�placement d'un personnage pour chaque pas
		NBETATSMARCHE = 4,	// nombre d'�tats de la marche (pour l'animation)
		NBETATSBLESSE = 2,	// nombre d'�tats du joueur bless�
		NBETATSMORT = 2 ;	// nombre d'�taits du joueur en train de mourir
	
	//--- chemins et noms de fichiers ---
	public static final String 
		CHEMIN = "../media/",
		CHEMINPERSOS = CHEMIN + "personnages/",
		CHEMINFONDS = CHEMIN + "fonds/",
		CHEMINMURS = CHEMIN + "murs/",
		CHEMINBOULES = CHEMIN + "boules/",
		CHEMINSONS = CHEMIN + "sons/",
		FONDCHOIX = CHEMINFONDS + "fondchoix.jpg",
		FONDARENE = CHEMINFONDS + "fondarene.jpg",
		PERSO = CHEMINPERSOS + "perso",
		MUR = CHEMINMURS + "mur.gif",
		BOULE = CHEMINBOULES + "boule.gif",
		EXTIMAGE = ".gif" ;

	//--- les sons ---
	public static final String
		SONSUIVANT   = CHEMINSONS + "suivant.wav",
		SONPRECEDENT = CHEMINSONS + "precedent.wav",
		SONGO        = CHEMINSONS + "go.wav",
		SONWELCOME   = CHEMINSONS + "welcome.wav",
		SONAMBIANCE  = CHEMINSONS + "ambiance.wav" ;
	public static final String[]
		SON = {"fight.wav", "hurt.wav", "death.wav"} ;
	public static final Integer
		FIGHT = 0,
		HURT = 1,
		DEATH = 2 ;
	
	//--- taille des images ---
	public static final int
		H_PERSO = 44,
		L_PERSO = 39,
		H_ARENE = 600,
		L_ARENE = 800,
		H_MUR = 35,
		L_MUR = 34,
		H_BOULE = 17,
		L_BOULE = 17,
		H_MESSAGE = 8,
		H_CHAT = 200,
		H_SAISIE = 25,
		MARGE = 5 ;

	//--- caract�re de s�paration ---
	public static final String SEPARE = "~" ;
	
	//--- valeur pour rep�rer le type d'envoi ---
	public static final int
		PSEUDO = 0,
		CHAT = 1,
		ACTION = 2 ;
	
}
