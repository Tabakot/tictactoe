package ru.tab.tictactoe.model;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Data
@Entity
@Table
@TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
    @OneToOne
    private Player player1;
    @OneToOne
    private Player player2;
    private GameStatus status;
    private String field;
    @Type(type = "int-array")
    @Column(
            name = "steps",
            columnDefinition = "integer[]"
    )
    private int[] steps;
    private TicToe winner;
    private TicToe currentStep;
}
