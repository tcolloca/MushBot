package command;

interface CommandValues {

	static final String COMMAND_INVALID = "command_invalid";

	static final String SUPERNESS = "superness";

	static final String LANG_NO_LANG = "lang_no_lang";
	static final String LANG_INVALID = "lang_invalid";
	static final String LANG_CHANGED = "lang_changed";
	static final String LANG_NO_AVL_LANGS = "lang_no_avl_langs";
	static final String LANG_ONE_AVL_LANG = "lang_one_avl_lang";
	static final String LANG_AVL_LANGS = "lang_avl_langs";

	static final String HELP_NO_COMMAND = "help_no_command";
	static final String HELP_INVALID = "help_invalid";
	static final String HELP = "help";
	
	public static final String MUSH_CREATE_NEW = "mush_create_new";
	public static final String MUSH_CREATE_ALREADY = "mush_create_already";
	
	public static final String MUSH_JOIN_NEW = "mush_join_new";
	public static final String MUSH_JOIN_ALREADY = "mush_join_already";
	public static final String MUSH_JOIN_INVALID = "mush_join_invalid";
	
	public static final String MUSH_START_NEW = "mush_start_new";
	public static final String MUSH_START_ALREADY = "mush_start_already";
	public static final String MUSH_START_INVALID = "mush_start_invalid";
}
