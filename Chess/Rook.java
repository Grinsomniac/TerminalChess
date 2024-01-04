package chess;

import java.util.*;

public class Rook extends Piece {
    public Rook(PieceFile pieceFile, int pieceRank, boolean isWhite) {
        super(pieceFile, pieceRank, isWhite);
    }

    @Override
    public PieceType getWhitePieceType() {
        return PieceType.WR;
    }

    @Override
    public PieceType getBlackPieceType() {
        return PieceType.BR;
    }

    public boolean getisWhite() {
        return isWhite;
    }

    @Override
    public ReturnPiece.PieceFile getPieceFile() {
        return pieceFile;
    }

    @Override
    public int getPieceRank() {
        return pieceRank;
    }

    @Override
    public boolean isMoveValid(int newRank, ReturnPiece.PieceFile newFile, ArrayList<ReturnPiece> piecesOnBoard,
            boolean playerWhite, char promotionPiece) {

        if (newRank == pieceRank && newFile == pieceFile) {

            return false;
        }

        int rankDifference = Math.abs(newRank - pieceRank);
        int fileDifference = Math.abs(newFile.ordinal() - pieceFile.ordinal());

        if (rankDifference != 0 && fileDifference != 0) {

            return false;
        }

        if (rankDifference == 0) {

            int fileDirection = Integer.compare(newFile.ordinal(), pieceFile.ordinal());
            for (PieceFile file = PieceFile.values()[pieceFile.ordinal()
                    + fileDirection]; file != newFile; file = PieceFile.values()[file.ordinal() + fileDirection]) {
                for (ReturnPiece piece : piecesOnBoard) {
                    if (piece.pieceRank == pieceRank && piece.pieceFile == file) {

                        return false;
                    }
                }
            }
        } else {

            int rankDirection = Integer.compare(newRank, pieceRank);
            for (int rank = pieceRank + rankDirection; rank != newRank; rank += rankDirection) {
                for (ReturnPiece piece : piecesOnBoard) {
                    if (piece.pieceRank == rank && piece.pieceFile == pieceFile) {

                        return false;

                    }
                }
            }
        }
        for (ReturnPiece piece : piecesOnBoard) {
            if (piece.pieceRank == newRank && piece.pieceFile.toString().charAt(0) == newFile.toString().charAt(0)) {

                if (piece.pieceType.toString().charAt(0) == 'W' && !playerWhite) {
                    if (piece.pieceType.toString().charAt(1) != 'K') {
                        capture(pieceFile, pieceRank, newFile, newRank, piecesOnBoard);
                    }
                    return true;
                } else {
                    if (piece.pieceType.toString().charAt(0) == 'B' && playerWhite) {
                        if (piece.pieceType.toString().charAt(1) != 'K') {

                            capture(pieceFile, pieceRank, newFile, newRank, piecesOnBoard);
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
