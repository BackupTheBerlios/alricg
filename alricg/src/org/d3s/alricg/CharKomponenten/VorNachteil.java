/*
 * Created 26. Dezember 2004 / 22:58:17
 * This file is part of the project ALRICG. The file is copyright
 * protected an under the GNU licence.
 * For more information see "http://alricg.die3sphaere.de/".
 */

package org.d3s.alricg.CharKomponenten;

import java.util.Hashtable;
import org.d3s.alricg.CharKomponenten.Links.Voraussetzung;

/**
 * <b>Beschreibung:</b><br> TODO Beschreibung einfügen
 * @author V.Strelow
 */
public class VorNachteil extends Class1 {
	private int gp;
	private int minStufe;
	private int maxStufe;
	private Vorteil[] verbietetVorteil;
	private Nachteil[] verbietetNachteil;
	private Hashtable aendertApSf;
	private Hashtable aendertGpVorteil;
	private Hashtable aendertGpNachteil;
	private boolean istMehrfachWaehlbar;
}
