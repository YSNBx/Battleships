import java.util.Arrays;

public class Ship {
    final char SHIP_SYMBOL = 'O';
    private int health;
    private String name;
    private int rowStart;
    private int rowEnd;
    private int columnStart;
    private int columnEnd;
    char[] cells;
    private boolean isPlaced = false;
    private boolean isSunken = false; //new

    public Ship(int health, String name) {
        this.health = health;
        this.name = name;
        this.cells = new char[health];
        Arrays.fill(this.cells, SHIP_SYMBOL);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public char[] getCells() {
        return cells;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getRowEnd() {
        return rowEnd;
    }

    public int getColumnStart() {
        return columnStart;
    }

    public int getColumnEnd() {
        return columnEnd;
    }

    public boolean isSunken() {
        return isSunken;
    }

    @Override
    public String toString() {
        return "Ship name: " + this.getName() + ", Health: " + this.getHealth();
    }

    public boolean setCoordinates(int rowStart, int columnStart, int rowEnd, int columnEnd) {
        if (rowStart != rowEnd && columnStart != columnEnd) {
            System.out.println("Error! Wrong ship location! Try again: ");
            return false;
        }

        if (rowStart == rowEnd) {
            if (columnEnd - columnStart == this.health - 1) {
                this.rowStart = rowStart;
                this.rowEnd = rowEnd;
                this.columnStart = columnStart;
                this.columnEnd = columnEnd;
                this.isPlaced = true;
                return true;
            } else {
                System.out.println("Error! Wrong length of the Battleship! Try again: ");
                return false;
            }
        } else {
            if (rowEnd - rowStart == this.health - 1) {
                this.rowStart = rowStart;
                this.rowEnd = rowEnd;
                this.columnStart = columnStart;
                this.columnEnd = columnEnd;
                this.isPlaced = true;
                return true;
            } else {
                System.out.println("Error! Wrong length of the Battleship! Try again: ");
                return false;
            }
        }
    }

    public boolean sunkShip(int index, char HIT) {
        this.cells[index] = HIT;
        for (char each : cells) {
            if (each != HIT) {
                return false;
            }
        }
        this.isSunken = true;
        return true;
    }
}
