package chess;

import java.util.*;

public class Bishop extends Piece {
    public Bishop(PieceFile pieceFile, int pieceRank, boolean isWhite) {
        super(pieceFile, pieceRank, isWhite);
    }

    @Override
    public PieceType getWhitePieceType() {
        return PieceType.WB;
    }

    @Override
    public PieceType getBlackPieceType() {
        return PieceType.BB;
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

        if (rankDifference != fileDifference) {
            return false;
        }

        int rankDirection = Integer.compare(newRank, pieceRank);
        int fileDirection = Integer.compare(newFile.ordinal(), pieceFile.ordinal());

        int currentRank = pieceRank + rankDirection;
        PieceFile currentFile = PieceFile.values()[pieceFile.ordinal() + fileDirection];
        while (currentRank != newRank && currentFile != newFile) {
            for (ReturnPiece piece : piecesOnBoard) {
                if (piece.pieceRank == currentRank && piece.pieceFile == currentFile) {
                    return false;
                }
            }
            currentRank += rankDirection;
            currentFile = PieceFile.values()[currentFile.ordinal() + fileDirection];
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
