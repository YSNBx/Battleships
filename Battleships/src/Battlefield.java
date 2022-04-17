import java.util.Scanner;

public class Battlefield {
    final int SIZE = 10;
    final int CHAR_TO_NUMBER = 65;
    final int COORDINATE_ADJUSTER = 1;
    final char EMPTY = '~';
    final char MISSED = 'M';
    final char HIT = 'X';
    final char PART_OF_SHIP = 'O';
    char[][] battlefield = new char[SIZE][SIZE];
    Ship[] ships = new Ship[5];

    public Battlefield() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                battlefield[i][j] = EMPTY;
            }
        }
    }

    public void setField() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(this);
        ships[0] = new Ship(SHIPTYPE.AIRCRAFT_HEALTH.getHealth(), "Aircraft Carrier");
        ships[1] = new Ship(SHIPTYPE.BATTLESHIP_HEALTH.getHealth(), "Battleship");
        ships[2] = new Ship(SHIPTYPE.SUBMARINE_HEALTH.getHealth(), "Submarine");
        ships[3] = new Ship(SHIPTYPE.CRUISER_HEALTH.getHealth(), "Cruiser");
        ships[4] = new Ship(SHIPTYPE.DESTROYER_HEALTH.getHealth(), "Destroyer");

        for(Ship ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getName(), ship.getHealth());
            while (true) {
                String[] coordinates = scanner.nextLine().split("\\s+");
                int rowStart = coordinates[0].charAt(0) - CHAR_TO_NUMBER;
                int columnStart = Integer.parseInt(coordinates[0].substring(1)) - COORDINATE_ADJUSTER;
                int rowEnd = coordinates[1].charAt(0) - CHAR_TO_NUMBER;
                int columnEnd = Integer.parseInt(coordinates[1].substring(1)) - COORDINATE_ADJUSTER;

                if (rowStart > rowEnd) {
                    int tmp = rowEnd;
                    rowEnd = rowStart;
                    rowStart = tmp;
                }

                if (columnStart > columnEnd) {
                    int tmp = columnEnd;
                    columnEnd = columnStart;
                    columnStart = tmp;
                }

                if (ship.setCoordinates(rowStart, columnStart, rowEnd, columnEnd)) {
                    if (this.putShipOnField(rowStart, columnStart, rowEnd, columnEnd, ship)) {
                        System.out.println(this);
                        break;
                    }
                }
            }
        }
    }

    private boolean putShipOnField(int rowStart, int columnStart, int rowEnd, int columnEnd, Ship _ship) {
        for (Ship ship : ships) {
            if (_ship != ship && ship.isPlaced()) {
                for (int i = rowStart - 1; i <= rowEnd + 1; i++) {
                    for (int j = columnStart - 1; j <= columnEnd + 1; j++) {
                        if ((i == ship.getRowStart() && j == ship.getColumnStart()) || (i == ship.getRowEnd() && j == ship.getColumnEnd())) {
                            System.out.println("Error! You placed it too close to another one. Try again: ");
                            return false;
                        }
                    }
                }
            }
        }

        if (rowStart == rowEnd) {
            for (int i = columnStart; i <= columnEnd; i++) {
                this.battlefield[rowStart][i] = _ship.getCells()[i - columnStart];
            }
        } else {
            for (int i = rowStart; i <= rowEnd; i++) {
                this.battlefield[i][columnStart] = _ship.getCells()[i - rowStart];
            }
        }
        return true;
    }

    public boolean shootCannon() {
        Scanner scanner = new Scanner(System.in);
        String coordinates = scanner.nextLine();
        int shotRow = coordinates.charAt(0) - CHAR_TO_NUMBER;
        int shotColumn = Integer.parseInt(coordinates.replaceAll("\\D+", "")) - COORDINATE_ADJUSTER;

        if ((shotRow < 0 || shotRow > 9) || (shotColumn < 0 || shotColumn > 9)) {
            System.out.println("Error! You entered the wrong coordinates! Try again: ");
        } else {
            boolean hitShip = false;
            boolean sunkShip = false;
            boolean endOfGame = true;

            for (Ship ship : ships) {
                if (shotRow == ship.getRowStart() && shotRow == ship.getRowEnd()) {
                    if (shotColumn >= ship.getColumnStart() && shotColumn <= ship.getColumnEnd()) {
                        hitShip = true;
                        sunkShip = ship.sunkShip(shotColumn - ship.getColumnStart(), HIT);
                        break;
                    }
                } else if (shotColumn == ship.getColumnStart() && shotColumn == ship.getColumnEnd()) {
                    if (shotRow >= ship.getRowStart() && shotRow <= ship.getRowEnd()) {
                        hitShip = true;
                        sunkShip = ship.sunkShip(shotRow - ship.getRowStart(), HIT);
                        break;
                    }
                }
            }

            if (hitShip && !sunkShip) {
                System.out.println("You hit a ship! Try again: ");
            } else if (sunkShip) {
                for (Ship ship : ships) {
                    if (!ship.isSunken()) {
                        System.out.println("You sank a ship! Specify a new target: ");
                        endOfGame = false;
                        break;
                    }
                }
                if (endOfGame) {
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    return true;
                }
            } else {
                this.battlefield[shotRow][shotColumn] = MISSED;
                printBattlefield(true);
                System.out.println("You missed! Try again: ");
            }
        }
        return false;
    }

    public void printBattlefield(boolean fogOfWar) {
        for (Ship ship : ships) {
            if (ship.getRowStart() == ship.getRowEnd()) {
                for (int i = ship.getColumnStart(); i <= ship.getColumnEnd(); i++) {
                    if (fogOfWar) {
                        this.battlefield[ship.getRowStart()][i] = ship.getCells()[i - ship.getColumnStart()] == PART_OF_SHIP ? EMPTY : ship.getCells()[i - ship.getColumnStart()];
                    } else {
                        this.battlefield[ship.getRowStart()][i] = ship.getCells()[i - ship.getColumnStart()];
                    }
                }
            } else {
                for (int i = ship.getRowStart(); i <= ship.getRowEnd(); i++) {
                    if (fogOfWar) {
                        this.battlefield[i][ship.getColumnStart()] = ship.getCells()[i - ship.getRowStart()] == PART_OF_SHIP ? EMPTY : ship.getCells()[i - ship.getRowStart()];
                    } else {
                        this.battlefield[i][ship.getColumnStart()] = ship.getCells()[i - ship.getRowStart()];
                    }
                }
            }
        }
        System.out.println(this);
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            result.append(Character.toChars(i + CHAR_TO_NUMBER));
            for (int j = 0; j < SIZE; j++) {
                result.append(" ").append(battlefield[i][j]);
            }
            result.append("\n");
        }
        return String.valueOf(result);
    }
}