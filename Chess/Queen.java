package chess;

import java.util.*;

public class Queen extends Piece {

    public Queen(PieceFile pieceFile, int pieceRank, boolean isWhite) {
        super(pieceFile, pieceRank, isWhite);
    }

    @Override
    public PieceType getWhitePieceType() {
        return PieceType.WQ;
    }

    @Override
    public PieceType getBlackPieceType() {
        return PieceType.BQ;
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

        int rankDirection = Integer.compare(newRank, pieceRank);
        int fileDirection = Integer.compare(newFile.ordinal(), pieceFile.ordinal());

        if (rankDifference == 0 || fileDifference == 0 || rankDifference == fileDifference) {

            int currentRank = pieceRank + rankDirection;
            PieceFile currentFile = PieceFile.values()[pieceFile.ordinal() + fileDirection];
            while (currentRank != newRank || currentFile != newFile) {
                for (ReturnPiece piece : piecesOnBoard) {
                    if (piece.pieceRank == currentRank && piece.pieceFile == currentFile) {

                        return false;
                    }
                }
                currentRank += rankDirection;
                currentFile = PieceFile.values()[currentFile.ordinal() + fileDirection];
            }

            for (ReturnPiece piece : piecesOnBoard) {
                if (piece.pieceRank == newRank && piece.pieceFile == newFile) {
                    if (piece.pieceType.toString().charAt(0) != pieceType.toString().charAt(0)) {

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
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
