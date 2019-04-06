import { Asset } from './Asset.model';

export class GroupAsset {
    id: number;
    activeFlg: number;
    code: string;
    name: string;
    type_of_asset: number;

    public constructor() {
        this.activeFlg = 1;
        this.type_of_asset = 1;
    }
}