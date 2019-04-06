import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OtherAssetComponent } from './other.asset.component';

describe('Other.AssetComponent', () => {
  let component: OtherAssetComponent;
  let fixture: ComponentFixture<OtherAssetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OtherAssetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OtherAssetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
