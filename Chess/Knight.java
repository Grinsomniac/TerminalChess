package chess;

import java.util.*;

public class Knight extends Piece {
    public Knight(PieceFile pieceFile, int pieceRank, boolean isWhite) {
        super(pieceFile, pieceRank, isWhite);
    }

    @Override
    public PieceType getWhitePieceType() {
        return PieceType.WN;
    }

    @Override
    public PieceType getBlackPieceType() {
        return PieceType.BN;
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

        int rankDifference = Math.abs(newRank - pieceRank);
        int fileDifference = Math.abs(newFile.ordinal() - pieceFile.ordinal());

        if ((rankDifference == 2 && fileDifference == 1) || (rankDifference == 1 && fileDifference == 2)) {

            for (ReturnPiece piece : piecesOnBoard) {
                if (piece.pieceRank == newRank
                        && piece.pieceFile.toString().charAt(0) == newFile.toString().charAt(0)) {

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

        return false;
    }

}
