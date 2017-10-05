import { TODOApp } from './app.po';

describe('TODO App', () => {
  let page: TODOApp;

  beforeEach(() => {
    page = new TODOApp();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to TODO Manager!!');
  });
});
