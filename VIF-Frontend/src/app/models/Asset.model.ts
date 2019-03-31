export class Asset {
    id: number;
    branchCode: string;
    assetCode: string;
    assetName: string;
    amount: number;
    orginalPrice: number;
    currentPrice: number;
    description: string;
    activeFlg: number;

    public constructor() {
        this.activeFlg = 1;
    }
}