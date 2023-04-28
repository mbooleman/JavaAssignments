public class Fraction {
    final int enumerator;
    final int denominator;

    public Fraction(int enumer,int denom){
        this.enumerator = enumer;
        this.denominator = denom;
    }

    public double toDecimalNotation(){
        double decimal = Math.round((Double.valueOf(enumerator) / denominator)*100000.0)/100000.0;
        return decimal;
    }
    @Override
    public String toString(){
        String text = "\""+enumerator+"/"+denominator+"\"";
        return text;
    }

    public Fraction add(int number){
        int enumeratorNew = enumerator+denominator*number;
        //int[] fractionNew = new int[]{enumeratorNew,denominator}
        Fraction fraction = new Fraction(enumeratorNew,denominator);
        return fraction;
    }

    public Fraction addFraction(Fraction Fraction){
        int enumeratorNew = enumerator*Fraction.denominator + Fraction.enumerator*denominator;
        int denominatorNew = denominator*Fraction.denominator;
        for (int i = Math.min(enumeratorNew,denominatorNew); i > 1;i--){
            if (enumeratorNew % i == 0 && denominatorNew % i == 0){
                enumeratorNew = enumeratorNew / i;
                denominatorNew = denominatorNew / i;
                if(enumeratorNew == 1){
                    break;
                }
            }
        }
    
        Fraction fraction = new Fraction(enumeratorNew,denominatorNew);
        return fraction;
    }

    public Fraction subtract(int number){
        int enumeratorNew = enumerator-denominator*number;
        //int[] fractionNew = new int[]{enumeratorNew,denominator}
        Fraction fraction = new Fraction(enumeratorNew,denominator);
        return fraction;
    }

    public Fraction subtractFraction(Fraction Fraction){
        int enumeratorNew = enumerator*Fraction.denominator - Fraction.enumerator*denominator;
        int denominatorNew = denominator*Fraction.denominator;
        for (int i = Math.min(enumeratorNew,denominatorNew); i > 1;i--){
            if (enumeratorNew % i == 0 && denominatorNew % i == 0){
                enumeratorNew = enumeratorNew / i;
                denominatorNew = denominatorNew / i;
                if(enumeratorNew == 1){
                    break;
                }
            }
        }

        Fraction fraction = new Fraction(enumeratorNew,denominatorNew);
        return fraction;
    }

    public Fraction multiply(int number){
        int enumeratorNew = number * enumerator;
        int denominatorNew = denominator;
        for (int i = Math.min(enumeratorNew,denominatorNew); i > 1;i--){
            if (enumeratorNew % i == 0 && denominatorNew % i == 0){
                enumeratorNew = enumeratorNew / i;
                denominatorNew = denominatorNew / i;
                if(enumeratorNew == 1){
                    break;
                }
            }
        }
        Fraction fraction = new Fraction(enumeratorNew,denominatorNew);
        return fraction;
    }

    public Fraction fractionMultiply(Fraction Fraction){
        int enumeratorNew = Fraction.enumerator * enumerator;
        int denominatorNew = Fraction.denominator*denominator;
        for (int i = Math.min(enumeratorNew,denominatorNew); i > 1;i--){
            if (enumeratorNew % i == 0 && denominatorNew % i == 0){
                enumeratorNew = enumeratorNew / i;
                denominatorNew = denominatorNew / i;
                if(enumeratorNew == 1){
                    break;
                }
            }
        }
        Fraction fraction = new Fraction(enumeratorNew,denominatorNew);
        return fraction;
    }

    public Fraction divide(int number){
        int enumeratorNew = enumerator ;
        int denominatorNew = denominator*number;
        for (int i = Math.min(enumeratorNew,denominatorNew); i > 1;i--){
            if (enumeratorNew % i == 0 && denominatorNew % i == 0){
                enumeratorNew = enumeratorNew / i;
                denominatorNew = denominatorNew / i;
                if(enumeratorNew == 1){
                    break;
                }
            }
        }
        Fraction fraction = new Fraction(enumeratorNew,denominatorNew);
        return fraction;
    }

    public Fraction fractionDivide(Fraction Fraction){
        int enumeratorNew = enumerator * Fraction.denominator ;
        int denominatorNew = denominator * Fraction.enumerator;
        for (int i = Math.min(enumeratorNew,denominatorNew); i > 1;i--){
            if (enumeratorNew % i == 0 && denominatorNew % i == 0){
                enumeratorNew = enumeratorNew / i;
                denominatorNew = denominatorNew / i;
                if(enumeratorNew == 1){
                    break;
                }
            }
        }
        Fraction fraction = new Fraction(enumeratorNew,denominatorNew);
        return fraction;
    }

    public static void main(String[] args){
        Fraction oneThird = new Fraction(1,3);
        System.out.println(oneThird.enumerator);
        System.out.println(oneThird.denominator);
        System.out.println(oneThird.toDecimalNotation());
        System.out.println(oneThird.toString());
        System.out.println(oneThird.add(1).toString());
        //Fraction oneSixth = new Fraction(1,6);
        Fraction oneSixth = new Fraction(1,6);
        System.out.println(oneThird.addFraction(oneSixth).toString());
        Fraction fourThird = new Fraction(4,3);
        System.out.println(fourThird.subtract(1).toString());
        Fraction oneHalf = new Fraction(1,2);
        System.out.println(oneHalf.subtractFraction(oneSixth).toString());
        Fraction threeFourth = new Fraction(3,4);
        System.out.println(threeFourth.multiply(2).toString());
        Fraction twoFifth = new Fraction(2,5);
        System.out.println(threeFourth.fractionMultiply(twoFifth).toString());
        Fraction threeSecond = new Fraction(3,2);
        System.out.println(threeSecond.divide(2).toString());
        Fraction threeTenth = new Fraction(3,10);
        System.out.println(threeTenth.fractionDivide(twoFifth).toString());

        //Trial
        Fraction twentythreeFifth = new Fraction(23,5);
        Fraction twelveFifthythree = new Fraction(12,53);
        System.out.println(twentythreeFifth.addFraction(twelveFifthythree).toString());
        System.out.println(twentythreeFifth.subtractFraction(twelveFifthythree).toString());
        System.out.println(twentythreeFifth.fractionMultiply(twelveFifthythree).toString());
        System.out.println(twentythreeFifth.fractionDivide(twelveFifthythree).toString());

    }
}