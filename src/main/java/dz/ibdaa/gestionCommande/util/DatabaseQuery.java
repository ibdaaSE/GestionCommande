package dz.ibdaa.gestionCommande.util;

public final class DatabaseQuery {

    public static final String SPLIT_FILTER = " ";

    /**
     * Retourne une requete de selection dans une seule table (sans join) en
     * utilisant des filtres sur les attributs donnés.
     *
     * @param table Nom de la table suivi du nom de la variable, par exemple :
     * "Centre c" ou "Article a"
     * @param attributs Attributs sur les quels on va appliquer la recherche, la
     * séparation entre les attributs se fait avec " " (espace), par exemple
     * :"reference designation", ces attributs seront concaténés et comparés
     * avec le filtre via la fonction "LIKE %filtre%"
     * @param filtres Filtres appliqués sur les attributs pour la recherche, si
     * les filtres sont vides alors une requete simple sera retournée sans
     * "WHERE", sinon si ce parametre contient des espaces, alors il sera devisé
     * en plusieurs filtres, chaque filtre sera comparé avec les attributs
     * donnés pour faire à la fin l'intersection des comparaisons avec "AND"
     * @return Requete complete d'une selection simple.
     */
    public static String getQuerySearch(String table, String attributs, String filtres) {
        return DatabaseQuery.getQuerySearch(table.split("\\s")[1], table, attributs, filtres);
    }

    public static String getQuerySearch(String variable, String table, String attributs, String filtres) {
        return DatabaseQuery.getQuerySearch(variable, table, attributs, filtres, "AND");
    }
    
    public static String getQuerySearch(String variable, String table, String attributs, String filtres, String comparator) {
        if (!filtres.trim().isEmpty()) {
            filtres = filtres.replace("'", "\\'");
            String[] filtresArray = filtres.split(SPLIT_FILTER);
            String queryString = "SELECT " + variable + " FROM " + table + " WHERE " + getQueryConcatAndLike(attributs, filtresArray[0]);
            for (int i = 1; i < filtresArray.length; i++) {
                queryString = queryString + " " + comparator + " " + getQueryConcatAndLike(attributs, filtresArray[i]);
            }
            return queryString;
        } else {
            return "SELECT " + variable + " FROM " + table;
        }
    }

    /**
     * Retourne une requete de selection dans une table en utilisant au moins
     * une jointure, chaque attribut donné sera comparé avec son filtre.
     *
     * @param tables ensemble des table à joindre, par exemple : "Batiment c
     * JOIN c.centre centre" (la premiere variable doit être toujours appelée
     * 'c')
     * @param joinAttributs Attributs sur les quels on va appliquer la
     * recherche, la séparation entre les attributs se fait avec " " (espace),
     * par exemple :"c.reference centre.designation"
     * @param joinFiltres Filtres appliqués sur les attributs pour la recherche,
     * la séparation entre les filtres se fait avec " " (espace), si l'un des
     * filres est vide il doit être représenté par "-1", les filtres seront
     * comparés avec les attributs un par un c'est à dire le 1er filtre avec le
     * 1er attribut, le 2eme filtre avec le 2eme attribut ... le N°eme filtre
     * avec le N°eme attribut pour faire à la fin l'intersection des
     * comparaisons avec "AND"
     * @return Requete complete d'une selection avec une jointure.
     */
    public static String getQueryJoin(String tables, String joinAttributs, String joinFiltres) {
        return getQueryJoin(tables.split("\\s")[1], tables, joinAttributs, joinFiltres);
    }

    public static String getQueryJoin(String variable, String tables, String joinAttributs, String joinFiltres) {
        String selectVariable = variable;
        if (joinFiltres.toLowerCase().replace("-1", "").trim().isEmpty()) {
            return "SELECT " + selectVariable + " FROM " + tables;
        }
        return "SELECT " + selectVariable + " FROM " + tables + " WHERE " + getQueryJoinWhere(joinAttributs, joinFiltres);
    }

    /**
     * Retourne la requete de combinaison entre une selection simple et une
     * selection avec jointure.
     *
     * @param tables ensemble des table à joindre, par exemple : "Batiment c
     * JOIN c.centre centre" (la premiere variable doit être toujours appelée
     * 'c')
     * @param attributs Attributs sur les quels on va appliquer la recherche, la
     * séparation entre les attributs se fait avec " " (espace), par exemple t
     * comparés avec le filtre via la fonction "LIKE %filtre%"
     * @param filtres Filtres appliqués sur les attributs pour la recherche, si
     * les filtres sont vides al * :"reference designation", ces attributs
     * seront concaténés et comparés ors une requete simple sera retournée sans
     * "WHERE", sinon si ce parametre contient des espaces, alors il sera devisé
     * en plusieurs filtres, chaque filtre sera comparé avec les attributs
     * donnés pour faire à la fin l'intersection des comparaison avec "AND"
     * @param joinAttributs Attributs sur les quels on va appliquer la
     * recherche, la séparation entre les attributs se fait avec " " (espace),
     * par exemple :"c.reference centre.designation"
     * @param joinFiltres Filtres appliqués sur les attributs pour la recherche,
     * la séparation entre les filtres se fait avec " " (espace), si l'un des
     * filres est vide il doit être représenté par "-1", les filtres seront
     * comparés avec les attributs un par un c'est à dire le 1er filtre avec le
     * 1er attribut, le 2eme filtre avec le 2eme attribut ... le N°eme filtre
     * avec le N°eme attribut pour faire à la fin l'intersection des
     * comparaisons avec "AND"
     * @return Requete complete d'une selection avec une jointure.
     */
    public static String getQueryJoinSearch(String tables, String attributs, String filtres, String joinAttributs, String joinFiltres) {
        return DatabaseQuery.getQueryJoinSearch(tables.split("\\s")[1], tables, attributs, filtres, joinAttributs, joinFiltres);
    }

    public final static String getQueryJoinSearch(String variable, String tables, String attributs, String filtres, String joinAttributs, String joinFiltres) {
        String queryString = DatabaseQuery.getQuerySearch(variable, tables, attributs, filtres);
        String wherePart = DatabaseQuery.getQueryJoinWhere(joinAttributs, joinFiltres);
        if (!wherePart.isEmpty()) {
            if (queryString.contains("WHERE")) {
                queryString = queryString + " and " + wherePart;
            } else {
                queryString = queryString + " WHERE " + wherePart;
            }
        }
        return queryString;
    }

    /**
     * Retourne la requete de combinaison entre une selection simple et une
     * selection avec ou sans jointure comparant deux dates (période).
     *
     * @param tables ensemble des table à joindre, par exemple : "Batiment c
     * JOIN c.centre centre" (la premiere variable doit être toujours appelée
     * 'c')
     * @param attributs Attributs sur les quels on va appliquer la recherche, la
     * séparation entre les attributs se fait avec " " (espace), par exemple
     * :"reference designation", ces attributs seront concaténés et comparés
     * avec le filtre via la fonction "LIKE %filtre%"
     * @param filtres Filtres appliqués sur les attributs pour la recherche, si
     * les filtres sont vides alors une requete simple sera retournée sans
     * "WHERE", sinon si ce parametre contient des espaces, alors il sera devisé
     * en plusieurs filtres, chaque filtre sera comparé avec les attributs
     * donnés pour faire à la fin l'intersection des comparaison avec "AND"
     * @param dateAttribut Attribut concérné pour la comparaison avec une date
     * de début et une autre de fin
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return Requête complete d'une selection avec une jointure comparant deux
     * date.
     */
    public static String getQueryDateSearch(String tables, String attributs, String filtres, String dateAttribut, String dateDebut, String dateFin) {
        return DatabaseQuery.getQueryDateSearch(tables.split("\\s")[1], tables, attributs, filtres, dateAttribut, dateDebut, dateFin);
    }

    public static String getQueryDateSearch(String variable, String tables, String attributs, String filtres, String dateAttribut, String dateDebut, String dateFin) {
        String queryString = DatabaseQuery.getQuerySearch(variable, tables, attributs, filtres);
        String wherePart = DatabaseQuery.getQueryJoinWhereDate(dateAttribut, dateDebut, dateFin);
        if (!wherePart.isEmpty()) {
            if (queryString.contains("WHERE")) {
                queryString = queryString + " and " + wherePart;
            } else {
                queryString = queryString + " WHERE " + wherePart;
            }
        }
        return queryString;
    }

    /**
     * Retourne la requete de combinaison entre une selection simple et une
     * jointure avec une comparaison de deux dates (période).
     *
     * @param tables ensemble des table à joindre, par exemple : "Batiment c
     * JOIN c.centre centre" (la premiere variable doit être toujours appelée
     * 'c')
     * @param attributs Attributs sur les quels on va appliquer la recherche, la
     * séparation entre les attributs se fait avec " " (espace), par exemple
     * :"reference designation", ces attributs seront concaténés et comparés
     * avec le filtre via la fonction "LIKE %filtre%"
     * @param filtres Filtres appliqués sur les attributs pour la recherche, si
     * les filtres sont vides alors une requete simple sera retournée sans
     * "WHERE", sinon si ce parametre contient des espaces, alors il sera devisé
     * en plusieurs filtres, chaque filtre sera comparé avec les attributs
     * donnés pour faire à la fin l'intersection des comparaison avec "AND"
     * @param joinAttributs Attributs sur les quels on va appliquer la
     * recherche, la séparation entre les attributs se fait avec " " (espace),
     * par exemple :"c.reference centre.designation"
     * @param joinFiltres Filtres appliqués sur les attributs pour la recherche,
     * la séparation entre les filtres se fait avec " " (espace), si l'un des
     * filres est vide il doit être représenté par "-1", les filtres seront
     * comparés avec les attributs un par un c'est à dire le 1er filtre avec le
     * 1er attribut, le 2eme filtre avec le 2eme attribut ... le N°eme filtre
     * avec le N°eme attribut pour faire à la fin l'intersection des
     * comparaisons avec "AND"
     * @param dateAttribut Attribut concérné pour la comparaison avec une date
     * de début et une autre de fin
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return Requête complete d'une selection avec une jointure comparant deux
     * date.
     */
    public static String getQueryJoinDateSearch(String tables, String attributs, String filtres, String joinAttributs, String joinFiltres, String dateAttribut, String dateDebut, String dateFin) {
        return DatabaseQuery.getQueryJoinDateSearch(tables.split("\\s")[1], tables, attributs, filtres, joinAttributs, joinFiltres, dateAttribut, dateDebut, dateFin);
    }

    public static String getQueryJoinDateSearch(String variable, String tables, String attributs, String filtres, String joinAttributs, String joinFiltres, String dateAttribut, String dateDebut, String dateFin) {
        String queryString = DatabaseQuery.getQueryJoinSearch(variable, tables, attributs, filtres, joinAttributs, joinFiltres);
        String wherePart = DatabaseQuery.getQueryJoinWhereDate(dateAttribut, dateDebut, dateFin);
        if (!wherePart.isEmpty()) {
            if (queryString.contains("WHERE")) {
                queryString = queryString + " and " + wherePart;
            } else {
                queryString = queryString + " WHERE " + wherePart;
            }
        }
        return queryString;
    }

    /**
     * Retourne la requete de selection avec ou sans jointure contenant une
     * comparaison de deux dates (période).
     *
     * @param tables ensemble des table à joindre, par exemple : "Batiment c
     * JOIN c.centre centre" (la premiere variable doit être toujours appelée
     * 'c')
     * @param dateAttribut Attribut concérné pour la comparaison avec une date
     * de début et une autre de fin
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return Requête complete d'une selection avec une jointure comparant deux
     * date.
     */
    public static String getQueryJoinDate(String tables, String dateAttribut, String dateDebut, String dateFin) {
        String date = dateDebut + dateFin;
        if (date.isEmpty()) {
            return "";
        }
        String selectVariable = tables.split("\\s")[1];
        return "SELECT " + selectVariable + " FROM " + tables + " WHERE " + getQueryJoinWhereDate(dateAttribut, dateDebut, dateFin);
    }

    /**
     * Retourne le "CONCAT" et le "LIKE" de plusieurs attributs avec un seul
     * filtre, utilisée généralement pour la génération d'une requête de
     * selection simple.
     *
     * @param selectVariable Nom de la variable représantant la table qui
     * contient les attributs
     * @param attributs Attributs qui seront concaténés
     * @param filtre Filtre comparé avec les attributs
     * @return la concaténation de plusieurs attributs et la comparaison avec un
     * seul filtre
     */
    private static String getQueryConcatAndLike(String attributs, String filtre) {
        String[] attributsArray = attributs.split("\\s");

        String concat;
        if (attributsArray.length == 1) {
            concat = "(coalesce(" + attributsArray[0] + ",''";
        } else {
            concat = "(concat(coalesce(" + attributsArray[0] + ",'')";
        }
        for (int i = 1; i < attributsArray.length; i++) {
            concat = concat + ",coalesce(" + attributsArray[i] + ",'')";
        }
        return concat + ") like '%" + filtre + "%')";
    }

    /**
     * Retourne la partie "WHERE" d'une requete de recherche contenant une
     * jointure.
     *
     * @param joinAttributs Attributs sur lesquels on va appliquer la recherche,
     * la séparation entre les attributs se fait avec " " (espace), par exemple
     * :"c.reference centre.designation"
     * @param joinFiltres Filtres appliqués sur les attributs pour la recherche,
     * la séparation entre les filtres se fait avec " " (espace), si l'un des
     * filres est vide il doit être représenté par "-1", les filtres seront
     * comparés avec les attributs un par un c'est à dire le 1er filtre avec le
     * 1er attribut, le 2eme filtre avec le 2eme attribut ... le N°eme filtre
     * avec le N°eme attribut pour faire à la fin l'intersection des
     * comparaisons avec "AND"
     * @return Partie "WHERE" d'une requete de recherche contenant une jointure.
     */
    private static String getQueryJoinWhere(String joinAttributs, String joinFiltres) {
        String[] attributsArray = joinAttributs.split("\\s");
        String[] filtersArray = joinFiltres.split("%");
        String where = "";
        if (!filtersArray[0].equals("-1")) {
            where = getWhereCondition(attributsArray[0], filtersArray[0]);
        }

        for (int i = 1; i < attributsArray.length; i++) {
            if (!filtersArray[i].toLowerCase().equals("-1")) {
                if (where.equals("")) {
                    where = getWhereCondition(attributsArray[i], filtersArray[i]);
                } else {
                    where = where + " and " + getWhereCondition(attributsArray[i], filtersArray[i]);
                }
            }
        }
        return where;
    }

    private static String getWhereCondition(String attribut, String filter) {
        String condition = "";
        if (attribut.startsWith("=")) {
            attribut = attribut.replace("=", "");
            return "(" + attribut + " = " + filter + ")";
        }
        if (attribut.startsWith("<=")) {
            attribut = attribut.replace("<=", "");
            return "(" + attribut + " <= " + filter + ")";
        }
        if (attribut.startsWith(">=")) {
            attribut = attribut.replace(">=", "");
            return "(" + attribut + " >= " + filter + ")";
        }
        if (attribut.startsWith("<>")) {
            attribut = attribut.replace("<>", "");
            return "(" + attribut + " <> " + filter + ")";
        }
        if (attribut.startsWith("<")) {
            attribut = attribut.replace("<", "");
            return "(" + attribut + " < " + filter + ")";
        }
        if (attribut.startsWith(">")) {
            attribut = attribut.replace(">", "");
            return "(" + attribut + " > " + filter + ")";
        }
        if (attribut.startsWith("isnn")) {
            attribut = attribut.replace("isnn", "");
            return "(" + attribut + " is not null)";
        }
        condition = "(coalesce(" + attribut + ",'') = '" + filter + "')";
        return condition;
    }

    /**
     * Retourne la partie "WHERE" d'une requete (avec ou sans jointure) de
     * comparaison des dates.
     *
     * @param dateAttribut Attribut concérné pour la comparaison avec une date
     * de début et une autre de fin
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return Partie "WHERE" d'une requete (avec ou sans jointure) de
     * comparaison des dates.
     */
    public static String getQueryJoinWhereDate(String dateAttribut, String dateDebut, String dateFin) {
        String date = dateDebut + dateFin;
        if (date.isEmpty()) {
            return "";
        }

        if (dateDebut.isEmpty() || dateFin.isEmpty()) {
            return "(coalesce(" + dateAttribut + ",''))" + " = '" + date + "'";
        }

        if (dateDebut.equals(dateFin)) {
            return "(coalesce(" + dateAttribut + ",''))" + " = '" + dateDebut + "'";
        }
        return "(coalesce(" + dateAttribut + ",'')" + " >= '" + dateDebut + "') and (coalesce(" + dateAttribut + ",'')" + " <= '" + dateFin + "')";
    }

}
