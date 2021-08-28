import { element, by, ElementFinder } from 'protractor';

export class PedidoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-pedido div table .btn-danger'));
  title = element.all(by.css('jhi-pedido div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class PedidoUpdatePage {
  pageTitle = element(by.id('jhi-pedido-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idPedidoInput = element(by.id('field_idPedido'));
  fechaPedidoInput = element(by.id('field_fechaPedido'));
  estadoSelect = element(by.id('field_estado'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdPedidoInput(idPedido: string): Promise<void> {
    await this.idPedidoInput.sendKeys(idPedido);
  }

  async getIdPedidoInput(): Promise<string> {
    return await this.idPedidoInput.getAttribute('value');
  }

  async setFechaPedidoInput(fechaPedido: string): Promise<void> {
    await this.fechaPedidoInput.sendKeys(fechaPedido);
  }

  async getFechaPedidoInput(): Promise<string> {
    return await this.fechaPedidoInput.getAttribute('value');
  }

  async setEstadoSelect(estado: string): Promise<void> {
    await this.estadoSelect.sendKeys(estado);
  }

  async getEstadoSelect(): Promise<string> {
    return await this.estadoSelect.element(by.css('option:checked')).getText();
  }

  async estadoSelectLastOption(): Promise<void> {
    await this.estadoSelect.all(by.tagName('option')).last().click();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class PedidoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-pedido-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-pedido'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
