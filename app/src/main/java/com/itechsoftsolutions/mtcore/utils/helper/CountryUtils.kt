package com.itechsoftsolutions.mtcore.utils.helper

import java.util.*

/**
 * This is a class that contains utils to work with country
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
object CountryUtils {

    /**
     * Fields
     */
    private val mCountryCodeMap: MutableMap<String, String> = TreeMap()
    private val mCountryNameMap: MutableMap<String, String> = TreeMap()
    private val mCountryKeyMap: MutableMap<String, String> = TreeMap()

    init {
        mCountryCodeMap["AF"] = "+93"
        mCountryCodeMap["AL"] = "+355"
        mCountryCodeMap["DZ"] = "+213"
        mCountryCodeMap["AS"] = "+1684"
        mCountryCodeMap["AD"] = "+376"
        mCountryCodeMap["AO"] = "+244"
        mCountryCodeMap["AI"] = "+1264"
        mCountryCodeMap["AG"] = "+1268"
        mCountryCodeMap["AR"] = "+54"
        mCountryCodeMap["AM"] = "+374"
        mCountryCodeMap["AW"] = "+297"
        mCountryCodeMap["AU"] = "+61"
        mCountryCodeMap["AT"] = "+43"
        mCountryCodeMap["AZ"] = "+994"
        mCountryCodeMap["BS"] = "+1242"
        mCountryCodeMap["BH"] = "+973"
        mCountryCodeMap["BD"] = "+880"
        mCountryCodeMap["BB"] = "+1246"
        mCountryCodeMap["BY"] = "+375"
        mCountryCodeMap["BE"] = "+32"
        mCountryCodeMap["BZ"] = "+501"
        mCountryCodeMap["BJ"] = "+229"
        mCountryCodeMap["BM"] = "+1441"
        mCountryCodeMap["BT"] = "+975"
        mCountryCodeMap["BO"] = "+591"
        mCountryCodeMap["BA"] = "+387"
        mCountryCodeMap["BW"] = "+267"
        mCountryCodeMap["BR"] = "+55"
        mCountryCodeMap["IO"] = "+246"
        mCountryCodeMap["VG"] = "+1284"
        mCountryCodeMap["BN"] = "+673"
        mCountryCodeMap["BG"] = "+359"
        mCountryCodeMap["BF"] = "+226"
        mCountryCodeMap["BI"] = "+257"
        mCountryCodeMap["KH"] = "+855"
        mCountryCodeMap["CM"] = "+237"
        mCountryCodeMap["CA"] = "+1"
        mCountryCodeMap["CV"] = "+238"
        mCountryCodeMap["KY"] = "+345"
        mCountryCodeMap["CF"] = "+236"
        mCountryCodeMap["TD"] = "+235"
        mCountryCodeMap["CL"] = "+56"
        mCountryCodeMap["CN"] = "+86"
        mCountryCodeMap["CX"] = "+61"
        mCountryCodeMap["CO"] = "+57"
        mCountryCodeMap["KM"] = "+269"
        mCountryCodeMap["CG"] = "+242"
        mCountryCodeMap["CD"] = "+243"
        mCountryCodeMap["CK"] = "+682"
        mCountryCodeMap["CR"] = "+506"
        mCountryCodeMap["HR"] = "+385"
        mCountryCodeMap["CU"] = "+53"
        mCountryCodeMap["CW"] = "+599"
        mCountryCodeMap["CY"] = "+537"
        mCountryCodeMap["CZ"] = "+420"
        mCountryCodeMap["DK"] = "+45"
        mCountryCodeMap["DJ"] = "+253"
        mCountryCodeMap["DM"] = "+1767"
        mCountryCodeMap["DO"] = "+1809"
        mCountryCodeMap["EC"] = "+593"
        mCountryCodeMap["EG"] = "+20"
        mCountryCodeMap["SV"] = "+503"
        mCountryCodeMap["GQ"] = "+240"
        mCountryCodeMap["ER"] = "+291"
        mCountryCodeMap["EE"] = "+372"
        mCountryCodeMap["ET"] = "+251"
        mCountryCodeMap["FK"] = "+500"
        mCountryCodeMap["FO"] = "+298"
        mCountryCodeMap["FJ"] = "+679"
        mCountryCodeMap["FI"] = "+358"
        mCountryCodeMap["FR"] = "+33"
        mCountryCodeMap["GF"] = "+594"
        mCountryCodeMap["PF"] = "+689"
        mCountryCodeMap["GA"] = "+241"
        mCountryCodeMap["GM"] = "+220"
        mCountryCodeMap["GE"] = "+995"
        mCountryCodeMap["DE"] = "+49"
        mCountryCodeMap["GH"] = "+233"
        mCountryCodeMap["GI"] = "+350"
        mCountryCodeMap["GR"] = "+30"
        mCountryCodeMap["GL"] = "+299"
        mCountryCodeMap["GD"] = "+1473"
        mCountryCodeMap["GP"] = "+590"
        mCountryCodeMap["GU"] = "+1671"
        mCountryCodeMap["GT"] = "+502"
        mCountryCodeMap["GN"] = "+224"
        mCountryCodeMap["GW"] = "+245"
        mCountryCodeMap["GY"] = "+595"
        mCountryCodeMap["HT"] = "+509"
        mCountryCodeMap["HN"] = "+504"
        mCountryCodeMap["HK"] = "+852"
        mCountryCodeMap["HU"] = "+36"
        mCountryCodeMap["IS"] = "+354"
        mCountryCodeMap["IN"] = "+91"
        mCountryCodeMap["ID"] = "+62"
        mCountryCodeMap["IR"] = "+98"
        mCountryCodeMap["IQ"] = "+964"
        mCountryCodeMap["IE"] = "+353"
        mCountryCodeMap["IL"] = "+972"
        mCountryCodeMap["IT"] = "+39"
        mCountryCodeMap["CI"] = "+225"
        mCountryCodeMap["JM"] = "+1876"
        mCountryCodeMap["JP"] = "+81"
        mCountryCodeMap["JO"] = "+962"
        mCountryCodeMap["KZ"] = "+77"
        mCountryCodeMap["KE"] = "+254"
        mCountryCodeMap["KI"] = "+686"
        mCountryCodeMap["KW"] = "+965"
        mCountryCodeMap["KG"] = "+996"
        mCountryCodeMap["LA"] = "+856"
        mCountryCodeMap["LV"] = "+371"
        mCountryCodeMap["LB"] = "+961"
        mCountryCodeMap["LS"] = "+266"
        mCountryCodeMap["LR"] = "+231"
        mCountryCodeMap["LY"] = "+218"
        mCountryCodeMap["LI"] = "+423"
        mCountryCodeMap["LT"] = "+370"
        mCountryCodeMap["LU"] = "+352"
        mCountryCodeMap["MO"] = "+853"
        mCountryCodeMap["MK"] = "+389"
        mCountryCodeMap["MG"] = "+261"
        mCountryCodeMap["MW"] = "+265"
        mCountryCodeMap["MY"] = "+60"
        mCountryCodeMap["MV"] = "+960"
        mCountryCodeMap["ML"] = "+223"
        mCountryCodeMap["MT"] = "+356"
        mCountryCodeMap["MH"] = "+692"
        mCountryCodeMap["MQ"] = "+596"
        mCountryCodeMap["MR"] = "+222"
        mCountryCodeMap["MU"] = "+230"
        mCountryCodeMap["YT"] = "+262"
        mCountryCodeMap["MX"] = "+52"
        mCountryCodeMap["FM"] = "+691"
        mCountryCodeMap["MD"] = "+373"
        mCountryCodeMap["MC"] = "+377"
        mCountryCodeMap["MN"] = "+976"
        mCountryCodeMap["ME"] = "+382"
        mCountryCodeMap["MS"] = "+1664"
        mCountryCodeMap["MA"] = "+212"
        mCountryCodeMap["MM"] = "+95"
        mCountryCodeMap["NA"] = "+264"
        mCountryCodeMap["NR"] = "+674"
        mCountryCodeMap["NP"] = "+977"
        mCountryCodeMap["NL"] = "+31"
        mCountryCodeMap["KN"] = "+1"
        mCountryCodeMap["NC"] = "+687"
        mCountryCodeMap["NZ"] = "+64"
        mCountryCodeMap["NI"] = "+505"
        mCountryCodeMap["NE"] = "+227"
        mCountryCodeMap["NG"] = "+234"
        mCountryCodeMap["NU"] = "+683"
        mCountryCodeMap["NF"] = "+672"
        mCountryCodeMap["KP"] = "+850"
        mCountryCodeMap["MP"] = "+1670"
        mCountryCodeMap["NO"] = "+47"
        mCountryCodeMap["OM"] = "+968"
        mCountryCodeMap["PK"] = "+92"
        mCountryCodeMap["PW"] = "+680"
        mCountryCodeMap["PA"] = "+507"
        mCountryCodeMap["PG"] = "+675"
        mCountryCodeMap["PY"] = "+595"
        mCountryCodeMap["PE"] = "+51"
        mCountryCodeMap["PH"] = "+63"
        mCountryCodeMap["PL"] = "+48"
        mCountryCodeMap["PT"] = "+351"
        mCountryCodeMap["PR"] = "+1787"
        mCountryCodeMap["QA"] = "+974"
        mCountryCodeMap["RE"] = "+262"
        mCountryCodeMap["RO"] = "+40"
        mCountryCodeMap["RU"] = "+7"
        mCountryCodeMap["RW"] = "+250"
        mCountryCodeMap["WS"] = "+685"
        mCountryCodeMap["SM"] = "+378"
        mCountryCodeMap["SA"] = "+966"
        mCountryCodeMap["SN"] = "+221"
        mCountryCodeMap["RS"] = "+381"
        mCountryCodeMap["SC"] = "+248"
        mCountryCodeMap["SL"] = "+232"
        mCountryCodeMap["SG"] = "+65"
        mCountryCodeMap["SK"] = "+421"
        mCountryCodeMap["SI"] = "+386"
        mCountryCodeMap["SB"] = "+677"
        mCountryCodeMap["ZA"] = "+27"
        mCountryCodeMap["GS"] = "+500"
        mCountryCodeMap["KR"] = "+82"
        mCountryCodeMap["ES"] = "+34"
        mCountryCodeMap["LK"] = "+94"
        mCountryCodeMap["SD"] = "+249"
        mCountryCodeMap["SR"] = "+597"
        mCountryCodeMap["SZ"] = "+268"
        mCountryCodeMap["SE"] = "+46"
        mCountryCodeMap["CH"] = "+41"
        mCountryCodeMap["SY"] = "+963"
        mCountryCodeMap["TW"] = "+886"
        mCountryCodeMap["TJ"] = "+992"
        mCountryCodeMap["TZ"] = "+255"
        mCountryCodeMap["TH"] = "+66"
        mCountryCodeMap["TL"] = "+670"
        mCountryCodeMap["TG"] = "+228"
        mCountryCodeMap["TK"] = "+690"
        mCountryCodeMap["TO"] = "+676"
        mCountryCodeMap["TT"] = "+1868"
        mCountryCodeMap["TN"] = "+216"
        mCountryCodeMap["TR"] = "+90"
        mCountryCodeMap["TM"] = "+993"
        mCountryCodeMap["TC"] = "+1649"
        mCountryCodeMap["TV"] = "+688"
        mCountryCodeMap["UG"] = "+256"
        mCountryCodeMap["UA"] = "+380"
        mCountryCodeMap["AE"] = "+971"
        mCountryCodeMap["GB"] = "+44"
        mCountryCodeMap["US"] = "+1"
        mCountryCodeMap["UY"] = "+598"
        mCountryCodeMap["UZ"] = "+998"
        mCountryCodeMap["VU"] = "+678"
        mCountryCodeMap["VE"] = "+58"
        mCountryCodeMap["VN"] = "+84"
        mCountryCodeMap["WF"] = "+681"
        mCountryCodeMap["YE"] = "+967"
        mCountryCodeMap["ZM"] = "+260"
        mCountryCodeMap["ZW"] = "+263"
        mCountryCodeMap["SO"] = "+252"
        mCountryCodeMap["UK"] = "+44"

        mCountryNameMap[CountryUtils.Country.ANDORRA] = "Andorra, Principality Of"
        mCountryNameMap[CountryUtils.Country.UNITED_ARAB_EMIRATES] = "United Arab Emirates"
        mCountryNameMap[CountryUtils.Country.AFGHANISTAN] = "Afghanistan, Islamic State Of"
        mCountryNameMap[CountryUtils.Country.ANTIGUA_AND_BARBUDA] = "Antigua And Barbuda"
        mCountryNameMap[CountryUtils.Country.ANGUILLA] = "Anguilla"
        mCountryNameMap[CountryUtils.Country.ALBANIA] = "Albania"
        mCountryNameMap[CountryUtils.Country.ARMENIA] = "Armenia"
        mCountryNameMap[CountryUtils.Country.ANGOLA] = "Angola"
        mCountryNameMap[CountryUtils.Country.ARGENTINA] = "Argentina"
        mCountryNameMap[CountryUtils.Country.AMERICAN_SAMOA] = "American Samoa"
        mCountryNameMap[CountryUtils.Country.AUSTRIA] = "Austria"
        mCountryNameMap[CountryUtils.Country.AUSTRALIA] = "Australia"
        mCountryNameMap[CountryUtils.Country.ARUBA] = "Aruba"
        mCountryNameMap[CountryUtils.Country.AZERBAIDJAN] = "Azerbaidjan"
        mCountryNameMap[CountryUtils.Country.BOSNIA_HERZEGOVINA] = "Bosnia-Herzegovina"
        mCountryNameMap[CountryUtils.Country.BARBADOS] = "Barbados"
        mCountryNameMap[CountryUtils.Country.BANGLADESH] = "Bangladesh"
        mCountryNameMap[CountryUtils.Country.BELGIUM] = "Belgium"
        mCountryNameMap[CountryUtils.Country.BURKINA_FASO] = "Burkina Faso"
        mCountryNameMap[CountryUtils.Country.BULGARIA] = "Bulgaria"
        mCountryNameMap[CountryUtils.Country.BAHRAIN] = "Bahrain"
        mCountryNameMap[CountryUtils.Country.BURUNDI] = "Burundi"
        mCountryNameMap[CountryUtils.Country.BENIN] = "Benin"
        mCountryNameMap[CountryUtils.Country.BERMUDA] = "Bermuda"
        mCountryNameMap[CountryUtils.Country.BRUNEI_DARUSSALAM] = "Brunei Darussalam"
        mCountryNameMap[CountryUtils.Country.BOLIVIA] = "Bolivia"
        mCountryNameMap[CountryUtils.Country.BRAZIL] = "Brazil"
        mCountryNameMap[CountryUtils.Country.BAHAMAS] = "Bahamas"
        mCountryNameMap[CountryUtils.Country.BHUTAN] = "Bhutan"
        mCountryNameMap[CountryUtils.Country.BOTSWANA] = "Botswana"
        mCountryNameMap[CountryUtils.Country.BELARUS] = "Belarus"
        mCountryNameMap[CountryUtils.Country.BELIZE] = "Belize"
        mCountryNameMap[CountryUtils.Country.CANADA] = "Canada"
        mCountryNameMap[CountryUtils.Country.CENTRAL_AFRICAN_REPUBLIC] = "Central African Republic"
        mCountryNameMap[CountryUtils.Country.CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE] = "Congo, The Democratic Republic Of The"
        mCountryNameMap[CountryUtils.Country.CONGO] = "Congo"
        mCountryNameMap[CountryUtils.Country.SWITZERLAND] = "Switzerland"
        mCountryNameMap[CountryUtils.Country.IVORY_COAST] = "Ivory Coast (Cote D'Ivoire)"
        mCountryNameMap[CountryUtils.Country.COOK_ISLANDS] = "Cook Islands"
        mCountryNameMap[CountryUtils.Country.CHILE] = "Chile"
        mCountryNameMap[CountryUtils.Country.CAMEROON] = "Cameroon"
        mCountryNameMap[CountryUtils.Country.CHINA] = "China"
        mCountryNameMap[CountryUtils.Country.COLOMBIA] = "Colombia"
        mCountryNameMap[CountryUtils.Country.COSTA_RICA] = "Costa Rica"
        mCountryNameMap[CountryUtils.Country.CUBA] = "Cuba"
        mCountryNameMap[CountryUtils.Country.CAPE_VERDE] = "Cape Verde"
        mCountryNameMap[CountryUtils.Country.CHRISTMAS_ISLAND] = "Christmas Island"
        mCountryNameMap[CountryUtils.Country.CYPRUS] = "Cyprus"
        mCountryNameMap[CountryUtils.Country.CZECH_REPUBLIC] = "Czech Republic"
        mCountryNameMap[CountryUtils.Country.GERMANY] = "Germany"
        mCountryNameMap[CountryUtils.Country.DJIBOUTI] = "Djibouti"
        mCountryNameMap[CountryUtils.Country.DENMARK] = "Denmark"
        mCountryNameMap[CountryUtils.Country.DOMINICA] = "Dominica"
        mCountryNameMap[CountryUtils.Country.DOMINICAN_REPUBLIC] = "Dominican Republic"
        mCountryNameMap[CountryUtils.Country.ALGERIA] = "Algeria"
        mCountryNameMap[CountryUtils.Country.ECUADOR] = "Ecuador"
        mCountryNameMap[CountryUtils.Country.ESTONIA] = "Estonia"
        mCountryNameMap[CountryUtils.Country.EGYPT] = "Egypt"
        mCountryNameMap[CountryUtils.Country.ERITREA] = "Eritrea"
        mCountryNameMap[CountryUtils.Country.SPAIN] = "Spain"
        mCountryNameMap[CountryUtils.Country.ETHIOPIA] = "Ethiopia"
        mCountryNameMap[CountryUtils.Country.FINLAND] = "Finland"
        mCountryNameMap[CountryUtils.Country.FIJI] = "Fiji"
        mCountryNameMap[CountryUtils.Country.FALKLAND_ISLANDS] = "Falkland Islands"
        mCountryNameMap[CountryUtils.Country.MICRONESIA] = "Micronesia"
        mCountryNameMap[CountryUtils.Country.FAROE_ISLANDS] = "Faroe Islands"
        mCountryNameMap[CountryUtils.Country.FRANCE] = "France"
        mCountryNameMap[CountryUtils.Country.GABON] = "Gabon"
        mCountryNameMap[CountryUtils.Country.GRENADA] = "Grenada"
        mCountryNameMap[CountryUtils.Country.GEORGIA] = "Georgia"
        mCountryNameMap[CountryUtils.Country.FRENCH_GUYANA] = "French Guyana"
        mCountryNameMap[CountryUtils.Country.GHANA] = "Ghana"
        mCountryNameMap[CountryUtils.Country.GIBRALTAR] = "Gibraltar"
        mCountryNameMap[CountryUtils.Country.GREENLAND] = "Greenland"
        mCountryNameMap[CountryUtils.Country.GAMBIA] = "Gambia"
        mCountryNameMap[CountryUtils.Country.GUINEA] = "Guinea"
        mCountryNameMap[CountryUtils.Country.GUADELOUPE_FRENCH] = "Guadeloupe (French)"
        mCountryNameMap[CountryUtils.Country.EQUATORIAL_GUINEA] = "Equatorial Guinea"
        mCountryNameMap[CountryUtils.Country.GREECE] = "Greece"
        mCountryNameMap[CountryUtils.Country.S_GEORGIA_S_SANDWICH_ISLS] = "S. Georgia & S. Sandwich Isls."
        mCountryNameMap[CountryUtils.Country.GUATEMALA] = "Guatemala"
        mCountryNameMap[CountryUtils.Country.GUAM_USA] = "Guam (USA)"
        mCountryNameMap[CountryUtils.Country.GUINEA_BISSAU] = "Guinea Bissau"
        mCountryNameMap[CountryUtils.Country.GUYANA] = "Guyana"
        mCountryNameMap[CountryUtils.Country.HONG_KONG] = "Hong Kong"
        mCountryNameMap[CountryUtils.Country.HONDURAS] = "Honduras"
        mCountryNameMap[CountryUtils.Country.CROATIA] = "Croatia"
        mCountryNameMap[CountryUtils.Country.HAITI] = "Haiti"
        mCountryNameMap[CountryUtils.Country.HUNGARY] = "Hungary"
        mCountryNameMap[CountryUtils.Country.INDONESIA] = "Indonesia"
        mCountryNameMap[CountryUtils.Country.IRELAND] = "Ireland"
        mCountryNameMap[CountryUtils.Country.ISRAEL] = "Israel"
        mCountryNameMap[CountryUtils.Country.INDIA] = "India"
        mCountryNameMap[CountryUtils.Country.BRITISH_INDIAN_OCEAN_TERRITORY] = "British Indian Ocean Territory"
        mCountryNameMap[CountryUtils.Country.IRAQ] = "Iraq"
        mCountryNameMap[CountryUtils.Country.IRAN] = "Iran"
        mCountryNameMap[CountryUtils.Country.ICELAND] = "Iceland"
        mCountryNameMap[CountryUtils.Country.ITALY] = "Italy"
        mCountryNameMap[CountryUtils.Country.JAMAICA] = "Jamaica"
        mCountryNameMap[CountryUtils.Country.JORDAN] = "Jordan"
        mCountryNameMap[CountryUtils.Country.JAPAN] = "Japan"
        mCountryNameMap[CountryUtils.Country.KENYA] = "Kenya"
        mCountryNameMap[CountryUtils.Country.KYRGYZ_REPUBLIC_KYRGYZSTAN] = "Kyrgyz Republic (Kyrgyzstan)"
        mCountryNameMap[CountryUtils.Country.CAMBODIA_KINGDOM_OF] = "Cambodia, Kingdom Of"
        mCountryNameMap[CountryUtils.Country.KIRIBATI] = "Kiribati"
        mCountryNameMap[CountryUtils.Country.COMOROS] = "Comoros"
        mCountryNameMap[CountryUtils.Country.SAINT_KITTS_NEVIS_ANGUILLA] = "Saint Kitts & Nevis Anguilla"
        mCountryNameMap[CountryUtils.Country.NORTH_KOREA] = "North Korea"
        mCountryNameMap[CountryUtils.Country.SOUTH_KOREA] = "South Korea"
        mCountryNameMap[CountryUtils.Country.KUWAIT] = "Kuwait"
        mCountryNameMap[CountryUtils.Country.CAYMAN_ISLANDS] = "Cayman Islands"
        mCountryNameMap[CountryUtils.Country.KAZAKHSTAN] = "Kazakhstan"
        mCountryNameMap[CountryUtils.Country.LAOS] = "Laos"
        mCountryNameMap[CountryUtils.Country.LEBANON] = "Lebanon"
        mCountryNameMap[CountryUtils.Country.LIECHTENSTEIN] = "Liechtenstein"
        mCountryNameMap[CountryUtils.Country.SRILANKA] = "Srilanka"
        mCountryNameMap[CountryUtils.Country.LIBERIA] = "Liberia"
        mCountryNameMap[CountryUtils.Country.LESOTHO] = "Lesotho"
        mCountryNameMap[CountryUtils.Country.LITHUANIA] = "Lithuania"
        mCountryNameMap[CountryUtils.Country.LUXEMBOURG] = "Luxembourg"
        mCountryNameMap[CountryUtils.Country.LATVIA] = "Latvia"
        mCountryNameMap[CountryUtils.Country.LIBYA] = "Libya"
        mCountryNameMap[CountryUtils.Country.MOROCCO] = "Morocco"
        mCountryNameMap[CountryUtils.Country.MONACO] = "Monaco"
        mCountryNameMap[CountryUtils.Country.MOLDAVIA] = "Moldavia"
        mCountryNameMap[CountryUtils.Country.MADAGASCAR] = "Madagascar"
        mCountryNameMap[CountryUtils.Country.MARSHALL_ISLANDS] = "Marshall Islands"
        mCountryNameMap[CountryUtils.Country.MACEDONIA] = "Macedonia"
        mCountryNameMap[CountryUtils.Country.MALI] = "Mali"
        mCountryNameMap[CountryUtils.Country.MYANMAR] = "Myanmar"
        mCountryNameMap[CountryUtils.Country.MONGOLIA] = "Mongolia"
        mCountryNameMap[CountryUtils.Country.MACAU] = "Macau"
        mCountryNameMap[CountryUtils.Country.NORTHERN_MARIANA_ISLANDS] = "Northern Mariana Islands"
        mCountryNameMap[CountryUtils.Country.MARTINIQUE_FRENCH] = "Martinique (French)"
        mCountryNameMap[CountryUtils.Country.MAURITANIA] = "Mauritania"
        mCountryNameMap[CountryUtils.Country.MONTSERRAT] = "Montserrat"
        mCountryNameMap[CountryUtils.Country.MALTA] = "Malta"
        mCountryNameMap[CountryUtils.Country.MAURITIUS] = "Mauritius"
        mCountryNameMap[CountryUtils.Country.MALDIVES] = "Maldives"
        mCountryNameMap[CountryUtils.Country.MALAWI] = "Malawi"
        mCountryNameMap[CountryUtils.Country.MEXICO] = "Mexico"
        mCountryNameMap[CountryUtils.Country.MALAYSIA] = "Malaysia"
        mCountryNameMap[CountryUtils.Country.NAMIBIA] = "Namibia"
        mCountryNameMap[CountryUtils.Country.NEW_CALEDONIA_FRENCH] = "New Caledonia (French)"
        mCountryNameMap[CountryUtils.Country.NIGER] = "Niger"
        mCountryNameMap[CountryUtils.Country.NORFOLK_ISLAND] = "Norfolk Island"
        mCountryNameMap[CountryUtils.Country.NIGERIA] = "Nigeria"
        mCountryNameMap[CountryUtils.Country.NICARAGUA] = "Nicaragua"
        mCountryNameMap[CountryUtils.Country.NETHERLANDS] = "Netherlands"
        mCountryNameMap[CountryUtils.Country.NORWAY] = "Norway"
        mCountryNameMap[CountryUtils.Country.NEPAL] = "Nepal"
        mCountryNameMap[CountryUtils.Country.NAURU] = "Nauru"
        mCountryNameMap[CountryUtils.Country.NIUE] = "Niue"
        mCountryNameMap[CountryUtils.Country.NEW_ZEALAND] = "New Zealand"
        mCountryNameMap[CountryUtils.Country.OMAN] = "Oman"
        mCountryNameMap[CountryUtils.Country.PANAMA] = "Panama"
        mCountryNameMap[CountryUtils.Country.PERU] = "Peru"
        mCountryNameMap[CountryUtils.Country.POLYNESIA_FRENCH] = "Polynesia (French)"
        mCountryNameMap[CountryUtils.Country.PAPUA_NEW_GUINEA] = "Papua New Guinea"
        mCountryNameMap[CountryUtils.Country.PHILIPPINES] = "Philippines"
        mCountryNameMap[CountryUtils.Country.PAKISTAN] = "Pakistan"
        mCountryNameMap[CountryUtils.Country.POLAND] = "Poland"
        mCountryNameMap[CountryUtils.Country.PUERTO_RICO] = "Puerto Rico"
        mCountryNameMap[CountryUtils.Country.PORTUGAL] = "Portugal"
        mCountryNameMap[CountryUtils.Country.PALAU] = "Palau"
        mCountryNameMap[CountryUtils.Country.PARAGUAY] = "Paraguay"
        mCountryNameMap[CountryUtils.Country.QATAR] = "Qatar"
        mCountryNameMap[CountryUtils.Country.REUNION_FRENCH] = "Reunion (French)"
        mCountryNameMap[CountryUtils.Country.ROMANIA] = "Romania"
        mCountryNameMap[CountryUtils.Country.RUSSIAN_FEDERATION] = "Russian Federation"
        mCountryNameMap[CountryUtils.Country.RWANDA] = "Rwanda"
        mCountryNameMap[CountryUtils.Country.SAUDI_ARABIA] = "Saudi Arabia"
        mCountryNameMap[CountryUtils.Country.SOLOMON_ISLANDS] = "Solomon Islands"
        mCountryNameMap[CountryUtils.Country.SEYCHELLES] = "Seychelles"
        mCountryNameMap[CountryUtils.Country.SUDAN] = "Sudan"
        mCountryNameMap[CountryUtils.Country.SWEDEN] = "Sweden"
        mCountryNameMap[CountryUtils.Country.SINGAPORE] = "Singapore"
        mCountryNameMap[CountryUtils.Country.SLOVENIA] = "Slovenia"
        mCountryNameMap[CountryUtils.Country.SLOVAK_REPUBLIC] = "Slovak Republic"
        mCountryNameMap[CountryUtils.Country.SIERRA_LEONE] = "Sierra Leone"
        mCountryNameMap[CountryUtils.Country.SAN_MARINO] = "San Marino"
        mCountryNameMap[CountryUtils.Country.SENEGAL] = "Senegal"
        mCountryNameMap[CountryUtils.Country.SOMALIA] = "Somalia"
        mCountryNameMap[CountryUtils.Country.SURINAME] = "Suriname"
        mCountryNameMap[CountryUtils.Country.EL_SALVADOR] = "El Salvador"
        mCountryNameMap[CountryUtils.Country.SYRIA] = "Syria"
        mCountryNameMap[CountryUtils.Country.SWAZILAND] = "Swaziland"
        mCountryNameMap[CountryUtils.Country.TURKS_AND_CAICOS_ISLANDS] = "Turks And Caicos Islands"
        mCountryNameMap[CountryUtils.Country.CHAD] = "Chad"
        mCountryNameMap[CountryUtils.Country.TOGO] = "Togo"
        mCountryNameMap[CountryUtils.Country.THAILAND] = "Thailand"
        mCountryNameMap[CountryUtils.Country.TADJIKISTAN] = "Tadjikistan"
        mCountryNameMap[CountryUtils.Country.TOKELAU] = "Tokelau"
        mCountryNameMap[CountryUtils.Country.TURKMENISTAN] = "Turkmenistan"
        mCountryNameMap[CountryUtils.Country.TUNISIA] = "Tunisia"
        mCountryNameMap[CountryUtils.Country.TONGA] = "Tonga"
        mCountryNameMap[CountryUtils.Country.TURKEY] = "Turkey"
        mCountryNameMap[CountryUtils.Country.TRINIDAD_AND_TOBAGO] = "Trinidad And Tobago"
        mCountryNameMap[CountryUtils.Country.TUVALU] = "Tuvalu"
        mCountryNameMap[CountryUtils.Country.TAIWAN] = "Taiwan"
        mCountryNameMap[CountryUtils.Country.TANZANIA] = "Tanzania"
        mCountryNameMap[CountryUtils.Country.UKRAINE] = "Ukraine"
        mCountryNameMap[CountryUtils.Country.UGANDA] = "Uganda"
        mCountryNameMap[CountryUtils.Country.UNITED_KINGDOM] = "United Kingdom"
        mCountryNameMap[CountryUtils.Country.UNITED_STATES] = "United States"
        mCountryNameMap[CountryUtils.Country.URUGUAY] = "Uruguay"
        mCountryNameMap[CountryUtils.Country.UZBEKISTAN] = "Uzbekistan"
        mCountryNameMap[CountryUtils.Country.VENEZUELA] = "Venezuela"
        mCountryNameMap[CountryUtils.Country.VIRGIN_ISLANDS_BRITISH] = "Virgin Islands (British)"
        mCountryNameMap[CountryUtils.Country.VIETNAM] = "Vietnam"
        mCountryNameMap[CountryUtils.Country.VANUATU] = "Vanuatu"
        mCountryNameMap[CountryUtils.Country.WALLIS_AND_FUTUNA_ISLANDS] = "Wallis And Futuna Islands"
        mCountryNameMap[CountryUtils.Country.SAMOA] = "Samoa"
        mCountryNameMap[CountryUtils.Country.YEMEN] = "Yemen"
        mCountryNameMap[CountryUtils.Country.MAYOTTE] = "Mayotte"
        mCountryNameMap[CountryUtils.Country.SOUTH_AFRICA] = "South Africa"
        mCountryNameMap[CountryUtils.Country.ZAMBIA] = "Zambia"
        mCountryNameMap[CountryUtils.Country.ZIMBABWE] = "Zimbabwe"

        mCountryKeyMap["Andorra, Principality Of"] = CountryUtils.Country.ANDORRA
        mCountryKeyMap["United Arab Emirates"] = CountryUtils.Country.UNITED_ARAB_EMIRATES
        mCountryKeyMap["Afghanistan, Islamic State Of"] = CountryUtils.Country.AFGHANISTAN
        mCountryKeyMap["Antigua And Barbuda"] = CountryUtils.Country.ANTIGUA_AND_BARBUDA
        mCountryKeyMap["Anguilla"] = CountryUtils.Country.ANGUILLA
        mCountryKeyMap["Albania"] = CountryUtils.Country.ALBANIA
        mCountryKeyMap["Armenia"] = CountryUtils.Country.ARMENIA
        mCountryKeyMap["Angola"] = CountryUtils.Country.ANGOLA
        mCountryKeyMap["Argentina"] = CountryUtils.Country.ARGENTINA
        mCountryKeyMap["American Samoa"] = CountryUtils.Country.AMERICAN_SAMOA
        mCountryKeyMap["Austria"] = CountryUtils.Country.AUSTRIA
        mCountryKeyMap["Australia"] = CountryUtils.Country.AUSTRALIA
        mCountryKeyMap["Aruba"] = CountryUtils.Country.ARUBA
        mCountryKeyMap["Azerbaidjan"] = CountryUtils.Country.AZERBAIDJAN
        mCountryKeyMap["Bosnia-Herzegovina"] = CountryUtils.Country.BOSNIA_HERZEGOVINA
        mCountryKeyMap["Barbados"] = CountryUtils.Country.BARBADOS
        mCountryKeyMap["Bangladesh"] = CountryUtils.Country.BANGLADESH
        mCountryKeyMap["Belgium"] = CountryUtils.Country.BELGIUM
        mCountryKeyMap["Burkina Faso"] = CountryUtils.Country.BURKINA_FASO
        mCountryKeyMap["Bulgaria"] = CountryUtils.Country.BULGARIA
        mCountryKeyMap["Bahrain"] = CountryUtils.Country.BAHRAIN
        mCountryKeyMap["Burundi"] = CountryUtils.Country.BURUNDI
        mCountryKeyMap["Benin"] = CountryUtils.Country.BENIN
        mCountryKeyMap["Bermuda"] = CountryUtils.Country.BERMUDA
        mCountryKeyMap["Brunei Darussalam"] = CountryUtils.Country.BRUNEI_DARUSSALAM
        mCountryKeyMap["Bolivia"] = CountryUtils.Country.BOLIVIA
        mCountryKeyMap["Brazil"] = CountryUtils.Country.BRAZIL
        mCountryKeyMap["Bahamas"] = CountryUtils.Country.BAHAMAS
        mCountryKeyMap["Bhutan"] = CountryUtils.Country.BHUTAN
        mCountryKeyMap["Botswana"] = CountryUtils.Country.BOTSWANA
        mCountryKeyMap["Belarus"] = CountryUtils.Country.BELARUS
        mCountryKeyMap["Belize"] = CountryUtils.Country.BELIZE
        mCountryKeyMap["Canada"] = CountryUtils.Country.CANADA
        mCountryKeyMap["Central African Republic"] = CountryUtils.Country.CENTRAL_AFRICAN_REPUBLIC
        mCountryKeyMap["Congo, The Democratic Republic Of The"] = CountryUtils.Country.CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE
        mCountryKeyMap["Congo"] = CountryUtils.Country.CONGO
        mCountryKeyMap["Switzerland"] = CountryUtils.Country.SWITZERLAND
        mCountryKeyMap["Ivory Coast (Cote D'Ivoire)"] = CountryUtils.Country.IVORY_COAST
        mCountryKeyMap["Cook Islands"] = CountryUtils.Country.COOK_ISLANDS
        mCountryKeyMap["Chile"] = CountryUtils.Country.CHILE
        mCountryKeyMap["Cameroon"] = CountryUtils.Country.CAMEROON
        mCountryKeyMap["China"] = CountryUtils.Country.CHINA
        mCountryKeyMap["Colombia"] = CountryUtils.Country.COLOMBIA
        mCountryKeyMap["Costa Rica"] = CountryUtils.Country.COSTA_RICA
        mCountryKeyMap["Cuba"] = CountryUtils.Country.CUBA
        mCountryKeyMap["Cape Verde"] = CountryUtils.Country.CAPE_VERDE
        mCountryKeyMap["Christmas Island"] = CountryUtils.Country.CHRISTMAS_ISLAND
        mCountryKeyMap["Cyprus"] = CountryUtils.Country.CYPRUS
        mCountryKeyMap["Czech Republic"] = CountryUtils.Country.CZECH_REPUBLIC
        mCountryKeyMap["Germany"] = CountryUtils.Country.GERMANY
        mCountryKeyMap["Djibouti"] = CountryUtils.Country.DJIBOUTI
        mCountryKeyMap["Denmark"] = CountryUtils.Country.DENMARK
        mCountryKeyMap["Dominica"] = CountryUtils.Country.DOMINICA
        mCountryKeyMap["Dominican Republic"] = CountryUtils.Country.DOMINICAN_REPUBLIC
        mCountryKeyMap["Algeria"] = CountryUtils.Country.ALGERIA
        mCountryKeyMap["Ecuador"] = CountryUtils.Country.ECUADOR
        mCountryKeyMap["Estonia"] = CountryUtils.Country.ESTONIA
        mCountryKeyMap["Egypt"] = CountryUtils.Country.EGYPT
        mCountryKeyMap["Eritrea"] = CountryUtils.Country.ERITREA
        mCountryKeyMap["Spain"] = CountryUtils.Country.SPAIN
        mCountryKeyMap["Ethiopia"] = CountryUtils.Country.ETHIOPIA
        mCountryKeyMap["Finland"] = CountryUtils.Country.FINLAND
        mCountryKeyMap["Fiji"] = CountryUtils.Country.FIJI
        mCountryKeyMap["Falkland Islands"] = CountryUtils.Country.FALKLAND_ISLANDS
        mCountryKeyMap["Micronesia"] = CountryUtils.Country.MICRONESIA
        mCountryKeyMap["Faroe Islands"] = CountryUtils.Country.FAROE_ISLANDS
        mCountryKeyMap["France"] = CountryUtils.Country.FRANCE
        mCountryKeyMap["Gabon"] = CountryUtils.Country.GABON
        mCountryKeyMap["Grenada"] = CountryUtils.Country.GRENADA
        mCountryKeyMap["Georgia"] = CountryUtils.Country.GEORGIA
        mCountryKeyMap["French Guyana"] = CountryUtils.Country.FRENCH_GUYANA
        mCountryKeyMap["Ghana"] = CountryUtils.Country.GHANA
        mCountryKeyMap["Gibraltar"] = CountryUtils.Country.GIBRALTAR
        mCountryKeyMap["Greenland"] = CountryUtils.Country.GREENLAND
        mCountryKeyMap["Gambia"] = CountryUtils.Country.GAMBIA
        mCountryKeyMap["Guinea"] = CountryUtils.Country.GUINEA
        mCountryKeyMap["Guadeloupe (French)"] = CountryUtils.Country.GUADELOUPE_FRENCH
        mCountryKeyMap["Equatorial Guinea"] = CountryUtils.Country.EQUATORIAL_GUINEA
        mCountryKeyMap["Greece"] = CountryUtils.Country.GREECE
        mCountryKeyMap["S. Georgia & S. Sandwich Isls."] = CountryUtils.Country.S_GEORGIA_S_SANDWICH_ISLS
        mCountryKeyMap["Guatemala"] = CountryUtils.Country.GUATEMALA
        mCountryKeyMap["Guam (USA)"] = CountryUtils.Country.GUAM_USA
        mCountryKeyMap["Guinea Bissau"] = CountryUtils.Country.GUINEA_BISSAU
        mCountryKeyMap["Guyana"] = CountryUtils.Country.GUYANA
        mCountryKeyMap["Hong Kong"] = CountryUtils.Country.HONG_KONG
        mCountryKeyMap["Honduras"] = CountryUtils.Country.HONDURAS
        mCountryKeyMap["Croatia"] = CountryUtils.Country.CROATIA
        mCountryKeyMap["Haiti"] = CountryUtils.Country.HAITI
        mCountryKeyMap["Hungary"] = CountryUtils.Country.HUNGARY
        mCountryKeyMap["Indonesia"] = CountryUtils.Country.INDONESIA
        mCountryKeyMap["Ireland"] = CountryUtils.Country.IRELAND
        mCountryKeyMap["Israel"] = CountryUtils.Country.ISRAEL
        mCountryKeyMap["India"] = CountryUtils.Country.INDIA
        mCountryKeyMap["British Indian Ocean Territory"] = CountryUtils.Country.BRITISH_INDIAN_OCEAN_TERRITORY
        mCountryKeyMap["Iraq"] = CountryUtils.Country.IRAQ
        mCountryKeyMap["Iran"] = CountryUtils.Country.IRAN
        mCountryKeyMap["Iceland"] = CountryUtils.Country.ICELAND
        mCountryKeyMap["Italy"] = CountryUtils.Country.ITALY
        mCountryKeyMap["Jamaica"] = CountryUtils.Country.JAMAICA
        mCountryKeyMap["Jordan"] = CountryUtils.Country.JORDAN
        mCountryKeyMap["Japan"] = CountryUtils.Country.JAPAN
        mCountryKeyMap["Kenya"] = CountryUtils.Country.KENYA
        mCountryKeyMap["Kyrgyz Republic (Kyrgyzstan)"] = CountryUtils.Country.KYRGYZ_REPUBLIC_KYRGYZSTAN
        mCountryKeyMap["Cambodia, Kingdom Of"] = CountryUtils.Country.CAMBODIA_KINGDOM_OF
        mCountryKeyMap["Kiribati"] = CountryUtils.Country.KIRIBATI
        mCountryKeyMap["Comoros"] = CountryUtils.Country.COMOROS
        mCountryKeyMap["Saint Kitts & Nevis Anguilla"] = CountryUtils.Country.SAINT_KITTS_NEVIS_ANGUILLA
        mCountryKeyMap["North Korea"] = CountryUtils.Country.NORTH_KOREA
        mCountryKeyMap["South Korea"] = CountryUtils.Country.SOUTH_KOREA
        mCountryKeyMap["Kuwait"] = CountryUtils.Country.KUWAIT
        mCountryKeyMap["Cayman Islands"] = CountryUtils.Country.CAYMAN_ISLANDS
        mCountryKeyMap["Kazakhstan"] = CountryUtils.Country.KAZAKHSTAN
        mCountryKeyMap["Laos"] = CountryUtils.Country.LAOS
        mCountryKeyMap["Lebanon"] = CountryUtils.Country.LEBANON
        mCountryKeyMap["Liechtenstein"] = CountryUtils.Country.LIECHTENSTEIN
        mCountryKeyMap["Srilanka"] = CountryUtils.Country.SRILANKA
        mCountryKeyMap["Liberia"] = CountryUtils.Country.LIBERIA
        mCountryKeyMap["Lesotho"] = CountryUtils.Country.LESOTHO
        mCountryKeyMap["Lithuania"] = CountryUtils.Country.LITHUANIA
        mCountryKeyMap["Luxembourg"] = CountryUtils.Country.LUXEMBOURG
        mCountryKeyMap["Latvia"] = CountryUtils.Country.LATVIA
        mCountryKeyMap["Libya"] = CountryUtils.Country.LIBYA
        mCountryKeyMap["Morocco"] = CountryUtils.Country.MOROCCO
        mCountryKeyMap["Monaco"] = CountryUtils.Country.MONACO
        mCountryKeyMap["Moldavia"] = CountryUtils.Country.MOLDAVIA
        mCountryKeyMap["Madagascar"] = CountryUtils.Country.MADAGASCAR
        mCountryKeyMap["Marshall Islands"] = CountryUtils.Country.MARSHALL_ISLANDS
        mCountryKeyMap["Macedonia"] = CountryUtils.Country.MACEDONIA
        mCountryKeyMap["Mali"] = CountryUtils.Country.MALI
        mCountryKeyMap["Myanmar"] = CountryUtils.Country.MYANMAR
        mCountryKeyMap["Mongolia"] = CountryUtils.Country.MONGOLIA
        mCountryKeyMap["Macau"] = CountryUtils.Country.MACAU
        mCountryKeyMap["Northern Mariana Islands"] = CountryUtils.Country.NORTHERN_MARIANA_ISLANDS
        mCountryKeyMap["Martinique (French)"] = CountryUtils.Country.MARTINIQUE_FRENCH
        mCountryKeyMap["Mauritania"] = CountryUtils.Country.MAURITANIA
        mCountryKeyMap["Montserrat"] = CountryUtils.Country.MONTSERRAT
        mCountryKeyMap["Malta"] = CountryUtils.Country.MALTA
        mCountryKeyMap["Mauritius"] = CountryUtils.Country.MAURITIUS
        mCountryKeyMap["Maldives"] = CountryUtils.Country.MALDIVES
        mCountryKeyMap["Malawi"] = CountryUtils.Country.MALAWI
        mCountryKeyMap["Mexico"] = CountryUtils.Country.MEXICO
        mCountryKeyMap["Malaysia"] = CountryUtils.Country.MALAYSIA
        mCountryKeyMap["Namibia"] = CountryUtils.Country.NAMIBIA
        mCountryKeyMap["New Caledonia (French)"] = CountryUtils.Country.NEW_CALEDONIA_FRENCH
        mCountryKeyMap["Niger"] = CountryUtils.Country.NIGER
        mCountryKeyMap["Norfolk Island"] = CountryUtils.Country.NORFOLK_ISLAND
        mCountryKeyMap["Nigeria"] = CountryUtils.Country.NIGERIA
        mCountryKeyMap["Nicaragua"] = CountryUtils.Country.NICARAGUA
        mCountryKeyMap["Netherlands"] = CountryUtils.Country.NETHERLANDS
        mCountryKeyMap["Norway"] = CountryUtils.Country.NORWAY
        mCountryKeyMap["Nepal"] = CountryUtils.Country.NEPAL
        mCountryKeyMap["Nauru"] = CountryUtils.Country.NAURU
        mCountryKeyMap["Niue"] = CountryUtils.Country.NIUE
        mCountryKeyMap["New Zealand"] = CountryUtils.Country.NEW_ZEALAND
        mCountryKeyMap["Oman"] = CountryUtils.Country.OMAN
        mCountryKeyMap["Panama"] = CountryUtils.Country.PANAMA
        mCountryKeyMap["Peru"] = CountryUtils.Country.PERU
        mCountryKeyMap["Polynesia (French)"] = CountryUtils.Country.POLYNESIA_FRENCH
        mCountryKeyMap["Papua New Guinea"] = CountryUtils.Country.PAPUA_NEW_GUINEA
        mCountryKeyMap["Philippines"] = CountryUtils.Country.PHILIPPINES
        mCountryKeyMap["Pakistan"] = CountryUtils.Country.PAKISTAN
        mCountryKeyMap["Poland"] = CountryUtils.Country.POLAND
        mCountryKeyMap["Puerto Rico"] = CountryUtils.Country.PUERTO_RICO
        mCountryKeyMap["Portugal"] = CountryUtils.Country.PORTUGAL
        mCountryKeyMap["Palau"] = CountryUtils.Country.PALAU
        mCountryKeyMap["Paraguay"] = CountryUtils.Country.PARAGUAY
        mCountryKeyMap["Qatar"] = CountryUtils.Country.QATAR
        mCountryKeyMap["Reunion (French)"] = CountryUtils.Country.REUNION_FRENCH
        mCountryKeyMap["Romania"] = CountryUtils.Country.ROMANIA
        mCountryKeyMap["Russian Federation"] = CountryUtils.Country.RUSSIAN_FEDERATION
        mCountryKeyMap["Rwanda"] = CountryUtils.Country.RWANDA
        mCountryKeyMap["Saudi Arabia"] = CountryUtils.Country.SAUDI_ARABIA
        mCountryKeyMap["Solomon Islands"] = CountryUtils.Country.SOLOMON_ISLANDS
        mCountryKeyMap["Seychelles"] = CountryUtils.Country.SEYCHELLES
        mCountryKeyMap["Sudan"] = CountryUtils.Country.SUDAN
        mCountryKeyMap["Sweden"] = CountryUtils.Country.SWEDEN
        mCountryKeyMap["Singapore"] = CountryUtils.Country.SINGAPORE
        mCountryKeyMap["Slovenia"] = CountryUtils.Country.SLOVENIA
        mCountryKeyMap["Slovak Republic"] = CountryUtils.Country.SLOVAK_REPUBLIC
        mCountryKeyMap["Sierra Leone"] = CountryUtils.Country.SIERRA_LEONE
        mCountryKeyMap["San Marino"] = CountryUtils.Country.SAN_MARINO
        mCountryKeyMap["Senegal"] = CountryUtils.Country.SENEGAL
        mCountryKeyMap["Somalia"] = CountryUtils.Country.SOMALIA
        mCountryKeyMap["Suriname"] = CountryUtils.Country.SURINAME
        mCountryKeyMap["El Salvador"] = CountryUtils.Country.EL_SALVADOR
        mCountryKeyMap["Syria"] = CountryUtils.Country.SYRIA
        mCountryKeyMap["Swaziland"] = CountryUtils.Country.SWAZILAND
        mCountryKeyMap["Turks And Caicos Islands"] = CountryUtils.Country.TURKS_AND_CAICOS_ISLANDS
        mCountryKeyMap["Chad"] = CountryUtils.Country.CHAD
        mCountryKeyMap["Togo"] = CountryUtils.Country.TOGO
        mCountryKeyMap["Thailand"] = CountryUtils.Country.THAILAND
        mCountryKeyMap["Tadjikistan"] = CountryUtils.Country.TADJIKISTAN
        mCountryKeyMap["Tokelau"] = CountryUtils.Country.TOKELAU
        mCountryKeyMap["Turkmenistan"] = CountryUtils.Country.TURKMENISTAN
        mCountryKeyMap["Tunisia"] = CountryUtils.Country.TUNISIA
        mCountryKeyMap["Tonga"] = CountryUtils.Country.TONGA
        mCountryKeyMap["Turkey"] = CountryUtils.Country.TURKEY
        mCountryKeyMap["Trinidad And Tobago"] = CountryUtils.Country.TRINIDAD_AND_TOBAGO
        mCountryKeyMap["Tuvalu"] = CountryUtils.Country.TUVALU
        mCountryKeyMap["Taiwan"] = CountryUtils.Country.TAIWAN
        mCountryKeyMap["Tanzania"] = CountryUtils.Country.TANZANIA
        mCountryKeyMap["Ukraine"] = CountryUtils.Country.UKRAINE
        mCountryKeyMap["Uganda"] = CountryUtils.Country.UGANDA
        mCountryKeyMap["United Kingdom"] = CountryUtils.Country.UNITED_KINGDOM
        mCountryKeyMap["United States"] = CountryUtils.Country.UNITED_STATES
        mCountryKeyMap["Uruguay"] = CountryUtils.Country.URUGUAY
        mCountryKeyMap["Uzbekistan"] = CountryUtils.Country.UZBEKISTAN
        mCountryKeyMap["Venezuela"] = CountryUtils.Country.VENEZUELA
        mCountryKeyMap["Virgin Islands (British)"] = CountryUtils.Country.VIRGIN_ISLANDS_BRITISH
        mCountryKeyMap["Vietnam"] = CountryUtils.Country.VIETNAM
        mCountryKeyMap["Vanuatu"] = CountryUtils.Country.VANUATU
        mCountryKeyMap["Wallis And Futuna Islands"] = CountryUtils.Country.WALLIS_AND_FUTUNA_ISLANDS
        mCountryKeyMap["Samoa"] = CountryUtils.Country.SAMOA
        mCountryKeyMap["Yemen"] = CountryUtils.Country.YEMEN
        mCountryKeyMap["Mayotte"] = CountryUtils.Country.MAYOTTE
        mCountryKeyMap["South Africa"] = CountryUtils.Country.SOUTH_AFRICA
        mCountryKeyMap["Zambia"] = CountryUtils.Country.ZAMBIA
        mCountryKeyMap["Zimbabwe"] = CountryUtils.Country.ZIMBABWE
    }

    /**
     * This method provides the country names
     *
     * @return [List] country names
     * */
    fun getCountryNames(): List<String> {
        return ArrayList(mCountryNameMap.values)
    }

    /**
     * This method provides the country codes
     *
     * @return [List] country codes
     * */
    fun getCountryCodes(): List<String> {
        return ArrayList(mCountryCodeMap.values)
    }

    /**
     * This method provides the country keys
     *
     * @return [List] country keys
     * */
    fun getCountryKeys(): List<String> {
        return ArrayList(mCountryKeyMap.values)
    }

    /**
     * This method provides the country code by country name
     *
     * @param countryName country name
     * @return [String] country code
     * */
    fun getCountryCodeByName(countryName: String): String {
        return getCountryCodeByKey(getCountryKeyByName(countryName))
    }

    /**
     * This method provides the country code by country key
     *
     * @param countryKey country key
     * @return [String] country code
     * */
    fun getCountryCodeByKey(countryKey: String): String {
        var countryCode: String = Constants.Default.DEFAULT_STRING

        if (mCountryCodeMap.containsKey(countryKey)) {
            countryCode = mCountryCodeMap[countryKey]!!
        }

        return countryCode
    }

    /**
     * This method provides the country key by country code
     *
     * @param countryCode country code
     * @return [String] country key
     * */
    fun getCountryKeyByCode(countryCode: String): String {
        var countryKey = Constants.Default.DEFAULT_STRING

        if (mCountryCodeMap.containsValue(countryCode)) {
            for ((key, value) in mCountryCodeMap) {
                if (value == countryCode) {
                    countryKey = key
                    break
                }
            }
        }

        return countryKey
    }

    /**
     * This method provides the country key by country name
     *
     * @param countryName country name
     * @return [String] country key
     * */
    fun getCountryKeyByName(countryName: String): String {
        var countryKey: String = Constants.Default.DEFAULT_STRING

        if (mCountryKeyMap.containsKey(countryName)) {
            countryKey = mCountryKeyMap[countryName]!!
        }

        return countryKey
    }

    /**
     * This method provides the country name by country key
     *
     * @param countryKey country key
     * @return [String] country name
     * */
    fun getCountryNameByKey(countryKey: String): String {
        var countryName: String = Constants.Default.DEFAULT_STRING

        if (mCountryNameMap.containsKey(countryKey)) {
            countryName = mCountryNameMap[countryKey]!!
        }

        return countryName
    }

    /**
     * This method provides the country name by country code
     *
     * @param countryCode country code
     * @return [String] country name
     * */
    fun getCountryNameByCode(countryCode: String): String {
        return getCountryNameByKey(getCountryKeyByCode(countryCode))
    }

    class Country {
        companion object {
            const val ANDORRA = "AD"
            const val UNITED_ARAB_EMIRATES = "AE"
            const val AFGHANISTAN = "AF"
            const val ANTIGUA_AND_BARBUDA = "AG"
            const val ANGUILLA = "AI"
            const val ALBANIA = "AL"
            const val ARMENIA = "AM"
            const val ANGOLA = "AO"
            const val ARGENTINA = "AR"
            const val AMERICAN_SAMOA = "AS"
            const val AUSTRIA = "AT"
            const val AUSTRALIA = "AU"
            const val ARUBA = "AW"
            const val AZERBAIDJAN = "AZ"
            const val BOSNIA_HERZEGOVINA = "BA"
            const val BARBADOS = "BB"
            const val BANGLADESH = "BD"
            const val BELGIUM = "BE"
            const val BURKINA_FASO = "BF"
            const val BULGARIA = "BG"
            const val BAHRAIN = "BH"
            const val BURUNDI = "BI"
            const val BENIN = "BJ"
            const val BERMUDA = "BM"
            const val BRUNEI_DARUSSALAM = "BN"
            const val BOLIVIA = "BO"
            const val BRAZIL = "BR"
            const val BAHAMAS = "BS"
            const val BHUTAN = "BT"
            const val BOTSWANA = "BW"
            const val BELARUS = "BY"
            const val BELIZE = "BZ"
            const val CANADA = "CA"
            const val CENTRAL_AFRICAN_REPUBLIC = "CF"
            const val CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE = "CD"
            const val CONGO = "CG"
            const val SWITZERLAND = "CH"
            const val IVORY_COAST = "CI"
            const val COOK_ISLANDS = "CK"
            const val CHILE = "CL"
            const val CAMEROON = "CM"
            const val CHINA = "CN"
            const val COLOMBIA = "CO"
            const val COSTA_RICA = "CR"
            const val CUBA = "CU"
            const val CAPE_VERDE = "CV"
            const val CHRISTMAS_ISLAND = "CX"
            const val CYPRUS = "CY"
            const val CZECH_REPUBLIC = "CZ"
            const val GERMANY = "DE"
            const val DJIBOUTI = "DJ"
            const val DENMARK = "DK"
            const val DOMINICA = "DM"
            const val DOMINICAN_REPUBLIC = "DO"
            const val ALGERIA = "DZ"
            const val ECUADOR = "EC"
            const val ESTONIA = "EE"
            const val EGYPT = "EG"
            const val ERITREA = "ER"
            const val SPAIN = "ES"
            const val ETHIOPIA = "ET"
            const val FINLAND = "FI"
            const val FIJI = "FJ"
            const val FALKLAND_ISLANDS = "FK"
            const val MICRONESIA = "FM"
            const val FAROE_ISLANDS = "FO"
            const val FRANCE = "FR"
            const val GABON = "GA"
            const val GRENADA = "GD"
            const val GEORGIA = "GE"
            const val FRENCH_GUYANA = "GF"
            const val GHANA = "GH"
            const val GIBRALTAR = "GI"
            const val GREENLAND = "GL"
            const val GAMBIA = "GM"
            const val GUINEA = "GN"
            const val GUADELOUPE_FRENCH = "GP"
            const val EQUATORIAL_GUINEA = "GQ"
            const val GREECE = "GR"
            const val S_GEORGIA_S_SANDWICH_ISLS = "GS"
            const val GUATEMALA = "GT"
            const val GUAM_USA = "GU"
            const val GUINEA_BISSAU = "GW"
            const val GUYANA = "GY"
            const val HONG_KONG = "HK"
            const val HONDURAS = "HN"
            const val CROATIA = "HR"
            const val HAITI = "HT"
            const val HUNGARY = "HU"
            const val INDONESIA = "ID"
            const val IRELAND = "IE"
            const val ISRAEL = "IL"
            const val INDIA = "IN"
            const val BRITISH_INDIAN_OCEAN_TERRITORY = "IO"
            const val IRAQ = "IQ"
            const val IRAN = "IR"
            const val ICELAND = "IS"
            const val ITALY = "IT"
            const val JAMAICA = "JM"
            const val JORDAN = "JO"
            const val JAPAN = "JP"
            const val KENYA = "KE"
            const val KYRGYZ_REPUBLIC_KYRGYZSTAN = "KG"
            const val CAMBODIA_KINGDOM_OF = "KH"
            const val KIRIBATI = "KI"
            const val COMOROS = "KM"
            const val SAINT_KITTS_NEVIS_ANGUILLA = "KN"
            const val NORTH_KOREA = "KP"
            const val SOUTH_KOREA = "KR"
            const val KUWAIT = "KW"
            const val CAYMAN_ISLANDS = "KY"
            const val KAZAKHSTAN = "KZ"
            const val LAOS = "LA"
            const val LEBANON = "LB"
            const val LIECHTENSTEIN = "LI"
            const val SRILANKA = "LK"
            const val LIBERIA = "LR"
            const val LESOTHO = "LS"
            const val LITHUANIA = "LT"
            const val LUXEMBOURG = "LU"
            const val LATVIA = "LV"
            const val LIBYA = "LY"
            const val MOROCCO = "MA"
            const val MONACO = "MC"
            const val MOLDAVIA = "MD"
            const val MADAGASCAR = "MG"
            const val MARSHALL_ISLANDS = "MH"
            const val MACEDONIA = "MK"
            const val MALI = "ML"
            const val MYANMAR = "MM"
            const val MONGOLIA = "MN"
            const val MACAU = "MO"
            const val NORTHERN_MARIANA_ISLANDS = "MP"
            const val MARTINIQUE_FRENCH = "MQ"
            const val MAURITANIA = "MR"
            const val MONTSERRAT = "MS"
            const val MALTA = "MT"
            const val MAURITIUS = "MU"
            const val MALDIVES = "MV"
            const val MALAWI = "MW"
            const val MEXICO = "MX"
            const val MALAYSIA = "MY"
            const val NAMIBIA = "NA"
            const val NEW_CALEDONIA_FRENCH = "NC"
            const val NIGER = "NE"
            const val NORFOLK_ISLAND = "NF"
            const val NIGERIA = "NG"
            const val NICARAGUA = "NI"
            const val NETHERLANDS = "NL"
            const val NORWAY = "NO"
            const val NEPAL = "NP"
            const val NAURU = "NR"
            const val NIUE = "NU"
            const val NEW_ZEALAND = "NZ"
            const val OMAN = "OM"
            const val PANAMA = "PA"
            const val PERU = "PE"
            const val POLYNESIA_FRENCH = "PF"
            const val PAPUA_NEW_GUINEA = "PG"
            const val PHILIPPINES = "PH"
            const val PAKISTAN = "PK"
            const val POLAND = "PL"
            const val PUERTO_RICO = "PR"
            const val PORTUGAL = "PT"
            const val PALAU = "PW"
            const val PARAGUAY = "PY"
            const val QATAR = "QA"
            const val REUNION_FRENCH = "RE"
            const val ROMANIA = "RO"
            const val RUSSIAN_FEDERATION = "RU"
            const val RWANDA = "RW"
            const val SAUDI_ARABIA = "SA"
            const val SOLOMON_ISLANDS = "SB"
            const val SEYCHELLES = "SC"
            const val SUDAN = "SD"
            const val SWEDEN = "SE"
            const val SINGAPORE = "SG"
            const val SLOVENIA = "SI"
            const val SLOVAK_REPUBLIC = "SK"
            const val SIERRA_LEONE = "SL"
            const val SAN_MARINO = "SM"
            const val SENEGAL = "SN"
            const val SOMALIA = "SO"
            const val SURINAME = "SR"
            const val EL_SALVADOR = "SV"
            const val SYRIA = "SY"
            const val SWAZILAND = "SZ"
            const val TURKS_AND_CAICOS_ISLANDS = "TC"
            const val CHAD = "TD"
            const val TOGO = "TG"
            const val THAILAND = "TH"
            const val TADJIKISTAN = "TJ"
            const val TOKELAU = "TK"
            const val TURKMENISTAN = "TM"
            const val TUNISIA = "TN"
            const val TONGA = "TO"
            const val TURKEY = "TR"
            const val TRINIDAD_AND_TOBAGO = "TT"
            const val TUVALU = "TV"
            const val TAIWAN = "TW"
            const val TANZANIA = "TZ"
            const val UKRAINE = "UA"
            const val UGANDA = "UG"
            const val UNITED_KINGDOM = "UK"
            const val UNITED_STATES = "US"
            const val URUGUAY = "UY"
            const val UZBEKISTAN = "UZ"
            const val VENEZUELA = "VE"
            const val VIRGIN_ISLANDS_BRITISH = "VG"
            const val VIETNAM = "VN"
            const val VANUATU = "VU"
            const val WALLIS_AND_FUTUNA_ISLANDS = "WF"
            const val SAMOA = "WS"
            const val YEMEN = "YE"
            const val MAYOTTE = "YT"
            const val SOUTH_AFRICA = "ZA"
            const val ZAMBIA = "ZM"
            const val ZIMBABWE = "ZW"
        }
    }
}