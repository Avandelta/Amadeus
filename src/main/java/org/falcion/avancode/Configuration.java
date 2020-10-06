package org.falcion.avancode;

public class Configuration {

    private sqlContext sqlContext;

    private Table[] tableArray;

    private int searchDelay;

    private boolean clearTime;
    private boolean oldSummarize;

    private String pointExpression;

    public void editConfiguration(sqlContext settings) {
        this.sqlContext = settings;
    }

    public void setTables(Table[] tableCount) {
        this.tableArray = tableCount;
    }

    public void setDelay(int delay) {
        this.searchDelay = delay;
    }

    public sqlContext getContext() {
        return this.sqlContext;
    }

    public Table[] getTables() {
        return this.tableArray;
    }

    public int getDelay() {
        return this.searchDelay;
    }

    public String getExpression() {
        return this.pointExpression;
    }

    public void setExpression(String experience) {
        this.pointExpression = experience;
    }

    public boolean isClearTime() {
        return this.clearTime;
    }

    public void setClearTime(boolean clear) {
        this.clearTime = clear;
    }

    public boolean isOldSummarize() {
        return this.oldSummarize;
    }

    public void setOldSummarizing(boolean old) {
        this.oldSummarize = old;
    }

    public static class sqlContext {

        private String databaseURL;
        private String databaseUser;
        private String databasePassword;

        private int timeOut;

        public void setURL(String URL) {
            this.databaseURL = URL;
        }

        public void setUser(String user) {
            this.databaseUser = user;
        }

        public void setPassword(String password) {
            this.databasePassword = password;
        }

        public void setTimeOut(int time) {
            this.timeOut = time;
        }

        public String getDatabaseURL() {
            return this.databaseURL;
        }

        public String getDatabaseUser() {
            return this.databaseUser;
        }

        public String getDatabasePassword() {
            return this.databasePassword;
        }

        public int getTimeOut() {
            return this.timeOut;
        }
    }

    public static class Table {
        private String databaseTable;
        private String createQuery;

        private String type;

        private String nameTable;
        private String nameTab;

        private int numberOfLines;

        private boolean activeTable;

        private int timeOfSummarize;

        private boolean enableSummarize;
        private boolean showLastWinners;

        private String[] tableDesc;

        private int[] moneyTrophy;
        private int[] topColor;

        public void setDatabaseTable(String table) {
            this.databaseTable = table;
        }

        public void setCreateQuery(String query) {
            this.createQuery = query;
        }

        public void setType(String tableType) {
            this.type = tableType;
        }

        public void setNameTable(String name) {
            this.nameTable = name;
        }

        public void setNameTab(String name) {
            this.nameTab = name;
        }

        public void setNumberOfLines(int lines) {
            this.numberOfLines = lines;
        }

        public void setActiveTable(boolean active) {
            this.activeTable = active;
        }

        public void setTimeOfSummarize(int time) {
            this.timeOfSummarize = time;
        }

        public void setEnableSummarize(boolean enable) {
            this.enableSummarize = enable;
        }

        public void setShowLastWinners(boolean lastWinners) {
            this.showLastWinners = lastWinners;
        }

        public void setTableDesc(String[] desc) {
            this.tableDesc = desc;
        }

        public void setMoneyTrophy(int[] trophy) {
            this.moneyTrophy = trophy;
        }

        public void setTopHEX(int[] lineColor) {
            this.topColor = lineColor;
        }

        public String getDatabaseTable() {
            return this.databaseTable;
        }

        public String getCreateQuery() {
            return this.createQuery;
        }

        public String getType() {
            return this.type;
        }

        public String getNameTable() {
            return this.nameTable;
        }

        public String getNameTab() {
            return this.nameTab;
        }

        public int getNumberOfLines() {
            return this.numberOfLines;
        }

        public boolean isActiveTable() {
            return this.activeTable;
        }

        public int getTimeOfSummarize() {
            return this.timeOfSummarize;
        }

        public boolean isEnableSummarize() {
            return this.enableSummarize;
        }

        public boolean isShowWinners() {
            return this.showLastWinners;
        }

        public String[] getTableDesc() {
            return this.tableDesc;
        }

        public int[] getMoneyTrophy() {
            return this.moneyTrophy;
        }

        public int[] getTopColor() {
            return this.topColor;
        }
    }
}
