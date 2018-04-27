package fr.info.game.logic.level.introduction.credits;

import fr.info.game.logic.level.introduction.IntroductionLevel;
import fr.info.game.logic.level.introduction.IntroductionPart;

import java.util.ArrayList;
import java.util.List;

public class IntroductionCredits extends IntroductionPart {

    private final List<IntroductionCredit> creditsList = new ArrayList<>();

    public IntroductionCredits(IntroductionLevel introductionLevel) {
        super(introductionLevel);
        this.creditsList.add(new IntroductionCredit(24, 5, 5, "Créé par:", 100));
        this.creditsList.add(new IntroductionCreditCharles());
        this.creditsList.add(new IntroductionCreditLoris());
        this.creditsList.add(new IntroductionCreditClement());
    }

    @Override
    public void update() {

        if (this.creditsList.size() != 0) {
            IntroductionCredit credit = this.creditsList.get(0);
            credit.update();

            if (credit.isFinished()) {
                nextCredit();
            }
        }

        super.update();
    }

    private void nextCredit() {
        this.creditsList.remove(0);
    }

    @Override
    public boolean isFinished() {
        return this.creditsList.size() == 0;
    }

    public IntroductionCredit getCurrentCredit() {
        if(this.creditsList.size() > 0) {
            return this.creditsList.get(0);
        } else {
            return null;
        }
    }
}
