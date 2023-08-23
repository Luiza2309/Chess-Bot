package src;

import java.util.ArrayList;

public class Pawn extends APiece {
    private boolean promoted = false;
    private APiece newPiece = null;

    public Pawn(PlaySide side) {
        super(side);
        super.value = 10;
        setType(Piece.PAWN);
    }

    public double[][] importanceW = {
            {0, 0.5, 0.5, 0, 0.5, 1, 5, 0},
            {0, 0.5, 0.5, 0, 0.5, 1, 5, 0},
            {0,   0,   0, 0,   1, 2, 5, 0},
            {0,   0,   0, 1,   2, 3, 5, 0},
            {0,   0,   0, 1,   2, 3, 5, 0},
            {0,   0,   0, 0,   1, 2, 5, 0},
            {0, 0.5, 0.5, 0, 0.5, 1, 5, 0},
            {0, 0.5, 0.5, 0, 0.5, 1, 5, 0}
    };



    public double[][] importanceB = {
            {0, 5, 1, 0.5, 0, 0.5, 0.5, 0},
            {0, 5, 1, 0.5, 0, 0.5, 0.5, 0},
            {0, 5, 2,   1, 0,   0,   0, 0},
            {0, 5, 3,   2, 1,   0,   0, 0},
            {0, 5, 3,   2, 1,   0,   0, 0},
            {0, 5, 2,   1, 0,   0,   0, 0},
            {0, 5, 1, 0.5, 0, 0.5, 0.5, 0},
            {0, 5, 1, 0.5, 0, 0.5, 0.5, 0}
    };

    @Override
    public double[][] getImportanceW() {
        return importanceW;
    }

    public double[][] getImportanceB() {
        return importanceB;
    }

    public APiece getNewPiece() {
        return newPiece;
    }

    public void setNewPiece(APiece newPiece) {
        this.newPiece = newPiece;
    }

    public void promote(APiece newPiece, Piece piece) {
        this.newPiece = newPiece;
        setType(piece);
        setPromoted(true);
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    @Override
    public ArrayList<Move> calculatePossibleMoves(String source, APiece[][] table) {
        if(isPromoted())
            return newPiece.calculatePossibleMoves(source, table);

        ArrayList<Move> moves = new ArrayList<>();
        int[] src = translateMoveToIndex(source);
        int[] temp = translateMoveToIndex(source);

        int direction;
        int[] conditions;

        if(getSide().equals(PlaySide.WHITE)) {
            direction = 1;
            conditions = new int[] {1, 7};
        } else {
            conditions = new int[] {6, 0};
            direction = -1;
        }

        if (src[1] == conditions[0]) {
            temp[1] = src[1] + 2 * direction;
            if (table[temp[0]][temp[1] - direction] == null && table[temp[0]][temp[1]] == null) {
                moves.add(Move.moveTo(source, translateIndexToMove(temp)));
            }
        }

        temp[1] = src[1] + direction;
        if(temp[1] == conditions[1] && table[temp[0]][temp[1]] == null) {
            moves.add(Move.promote(source,translateIndexToMove(temp), Piece.QUEEN));
        } else {
            if (table[temp[0]][temp[1]] == null) {
                moves.add(Move.moveTo(source, translateIndexToMove(temp)));
            }
        }

        temp[0] = src[0] + 1;
        if (temp[0] < 8) {
            if (table[temp[0]][temp[1]] != null && table[temp[0]][temp[1]].getSide() != this.getSide()) {
                if (temp[1] == conditions[1]) {
                    Move m = Move.promote(source,translateIndexToMove(temp), Piece.QUEEN);
                    m.valueCapt = table[temp[0]][temp[1]].value;
                    m.setCapture(true);
                    moves.add(m);
                } else {
                    Move m = Move.moveTo(source, translateIndexToMove(temp));
                    m.valueCapt = table[temp[0]][temp[1]].value;
                    m.setCapture(true);
                    moves.add(m);
                }
            }
        }

        temp[0] = src[0] - 1;
        if (temp[0] >= 0) {
            if (table[temp[0]][temp[1]] != null && table[temp[0]][temp[1]].getSide() != this.getSide()) {
                if (temp[1] == conditions[1]) {
                    Move m = Move.promote(source,translateIndexToMove(temp), Piece.QUEEN);
                    m.valueCapt = table[temp[0]][temp[1]].value;
                    m.setCapture(true);
                    moves.add(m);
                } else {
                    Move m = Move.moveTo(source, translateIndexToMove(temp));
                    m.valueCapt = table[temp[0]][temp[1]].value;
                    m.setCapture(true);
                    moves.add(m);
                }
            }
        }

        return moves;
    }
}
