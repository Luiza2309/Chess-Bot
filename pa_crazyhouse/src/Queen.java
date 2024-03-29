package src;

import java.util.ArrayList;

public class Queen extends APiece {
    public Queen(PlaySide side) {
        super(side);
        super.value = 80;
        setType(Piece.QUEEN);
    }

    public double[][] importanceW = {
            {  -2,  -1,  -1,    0, -0.5,  -1, -1,   -2},
            {  -1,   0, 0.5,    0,    0,   0,  0,   -1},
            {  -1, 0.5, 0.5,  0.5,  0.5, 0.5,  0,   -1},
            {-0.5,   0, 0.5,  0.5,  0.5, 0.5,  0, -0.5},
            {-0.5,   0, 0.5,  0.5,  0.5, 0.5,  0, -0.5},
            {  -1,   0, 0.5,  0.5,  0.5, 0.5,  0,   -1},
            {  -1,   0,   0,    0,    0,   0,  0,   -1},
            {  -2,  -1,  -1, -0.5, -0.5,  -1,  0,   -2}
    };

    public double[][] importanceB = {
            {  -2, -1,  -1, -0.5,    0,  -1,  -1,   -2},
            {  -1,  0,   0,    0,    0, 0.5,   0,   -1},
            {  -1,  0, 0.5,  0.5,  0.5, 0.5, 0.5,   -1},
            {-0.5,  0, 0.5,  0.5,  0.5, 0.5,   0, -0.5},
            {-0.5,  0, 0.5,  0.5,  0.5, 0.5,   0, -0.5},
            {  -1,  0, 0.5,  0.5,  0.5, 0.5,   0,   -1},
            {  -1,  0,   0,    0,    0,   0,   0,   -1},
            {  -2,  0,  -1, -0.5, -0.5,  -1,  -1,   -2}
    };


    @Override
    public double[][] getImportanceW() {
        return importanceW;
    }

    public double[][] getImportanceB() {
        return importanceB;
    }

    // regina
    @Override
    public ArrayList<Move> calculatePossibleMoves(String source, APiece[][] table) {
        ArrayList<Move> moves = new ArrayList<>();
        int[] src = translateMoveToIndex(source);
        String dest;

        // partea de tura din regina
        // a -> h
        for (int i = 1; i < 8; i++) {
            if(src[0] + i < 8) {
                if(table[src[0] + i][src[1]] == null) {
                    dest = translateIndexToMove(new int[] {src[0] + i, src[1]});
                    moves.add(Move.moveTo(source, dest));
                } else if(table[src[0] + i][src[1]].getSide() != this.getSide()) {
                    dest = translateIndexToMove(new int[] {src[0] + i, src[1]});
                    Move m = Move.moveTo(source, dest);
                    m.valueCapt = table[src[0] + i][src[1]].value;
                    m.setCapture(true);
                    moves.add(m);
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // h -> a
        for(int i = 1; i < 8; i++) {
            if(src[0] - i >= 0) {
                if(table[src[0] - i][src[1]] == null) {
                    dest = translateIndexToMove(new int[] {src[0] - i, src[1]});
                    moves.add(Move.moveTo(source, dest));
                } else if(table[src[0] - i][src[1]].getSide() != this.getSide()) {
                    dest = translateIndexToMove(new int[] {src[0] - i, src[1]});
                    Move m = Move.moveTo(source, dest);
                    m.valueCapt = table[src[0] - i][src[1]].value;
                    m.setCapture(true);
                    moves.add(m);
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // 8 -> 1
        for(int i = 1; i < 8; i++) {
            if(src[1] - i >= 0) {
                if(table[src[0]][src[1] - i] == null) {
                    dest = translateIndexToMove(new int[] {src[0], src[1] - i});
                    moves.add(Move.moveTo(source, dest));
                } else if(table[src[0]][src[1] - i].getSide() != this.getSide()) {
                    dest = translateIndexToMove(new int[] {src[0], src[1] - i});
                    Move m = Move.moveTo(source, dest);
                    m.valueCapt = table[src[0]][src[1] - i].value;
                    m.setCapture(true);
                    moves.add(m);
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        // 1 -> 8
        for(int i = 1; i < 8; i++) {
            if(src[1] + i < 8) {
                if(table[src[0]][src[1] + i] == null) {
                    dest = translateIndexToMove(new int[] {src[0], src[1] + i});
                    moves.add(Move.moveTo(source, dest));
                } else if(table[src[0]][src[1] + i].getSide() != this.getSide()) {
                    dest = translateIndexToMove(new int[] {src[0], src[1] + i});
                    Move m = Move.moveTo(source, dest);
                    m.valueCapt = table[src[0]][src[1] + i].value;
                    m.setCapture(true);
                    moves.add(m);
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }


        // partea de nebun din regina
        int i = 1; // sa se deplaseze pe linii
        int j = 1; // sa se deplaseze pe coloane

        int stop = 0;
        int[] stops = new int[4];
        // 0 -> lu; 1 -> ld; 2 -> ru; 3 -> rd

        while(stops[0] == 0 || stops[1] == 0 || stops[2] == 0 || stops[3] == 0) {
            int left = src[1] - j;
            int right = src[1] + j;
            int up = src[0] + i;
            int down = src[0] - i;


            if(left >= 0) {
                // merge sa lu si ld
                if(up < 8) {
                    // inca e in tabla
                    if(stops[0] == 0) {
                        // inca nu si-a luat break pe directia asta
                        if(table[up][left] == null) {
                            dest = translateIndexToMove(new int[] {up, left});
                            moves.add(Move.moveTo(source, dest));
                        } else if(table[up][left].getSide() != this.getSide()) {
                            dest = translateIndexToMove(new int[] {up, left});
                            Move m = Move.moveTo(source, dest);
                            m.setCapture(true);
                            m.valueCapt = table[up][left].value;
                            moves.add(m);
                            stops[0]++;
                        } else {
                            // e acelasi side ca mine
                            stops[0]++;
                        }
                    }
                } else {
                    stops[0]++;
                }

                if(down >= 0) {
                    // inca e in tabla
                    if(stops[1] == 0) {
                        // inca nu si-a luat break pe directia asta
                        if(table[down][left] == null) {
                            dest = translateIndexToMove(new int[] {down, left});
                            moves.add(Move.moveTo(source, dest));
                        } else if(table[down][left].getSide() != this.getSide()) {
                            dest = translateIndexToMove(new int[] {down, left});
                            Move m = Move.moveTo(source, dest);
                            m.setCapture(true);
                            m.valueCapt = table[down][left].value;
                            moves.add(m);
                            stops[1]++;
                        } else {
                            // e acelasi side ca mine
                            stops[1]++;
                        }
                    }
                } else {
                    stops[1]++;
                }
            } else {
                stops[0]++;
                stops[1]++;
            }


            if(right < 8) {
                // puteam sa ru si rd
                if(up < 8) {
                    // inca e in tabla
                    if(stops[2] == 0) {
                        // inca nu si-a luat break pe directia asta
                        if(table[up][right] == null) {
                            dest = translateIndexToMove(new int[] {up, right});
                            moves.add(Move.moveTo(source, dest));
                        } else if(table[up][right].getSide() != this.getSide()) {
                            dest = translateIndexToMove(new int[] {up, right});
                            Move m = Move.moveTo(source, dest);
                            m.setCapture(true);
                            m.valueCapt = table[up][right].value;
                            moves.add(m);
                            stops[2]++;
                        } else {
                            // e acelasi side ca mine
                            stops[2]++;
                        }
                    }
                } else {
                    stops[2]++;
                }

                if(down >= 0) {
                    // inca e in tabla
                    if(stops[3] == 0) {
                        // inca nu si-a luat break pe directia asta
                        if(table[down][right] == null) {
                            dest = translateIndexToMove(new int[] {down, right});
                            moves.add(Move.moveTo(source, dest));
                        } else if(table[down][right].getSide() != this.getSide()) {
                            dest = translateIndexToMove(new int[] {down, right});
                            Move m = Move.moveTo(source, dest);
                            m.valueCapt = table[down][right].value;
                            m.setCapture(true);
                            moves.add(m);
                            stops[3]++;
                        } else {
                            // e acelasi side ca mine
                            stops[3]++;
                        }
                    }
                } else {
                    stops[3]++;
                }
            } else {
                stops[2]++;
                stops[3]++;
            }

            i++;
            j++;
        }

        return moves;
    }
}
