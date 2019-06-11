package trabalho.aps;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class CadastroLanchonete {
	
	public void mostrarMenu() {
		int opcao;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("MENU INICIAL LANCHONETE");
			System.out.println("Escolha uma opção: ");
			System.out.println("1. Realizar cadastro.");
			System.out.println("2. Fazer pedido.");
			System.out.println("3. Sair");
			opcao = scanner.nextInt();
			
			try {
				switch(opcao) {
				case 1:
					cadastro();
					break;
				case 2:
					pedido();
					break;
				}
			} catch(Exception e) {
				System.out.println("Não foi possivel realizar a operação solicitada. Tente novamente.");
			}
		} while(opcao != 3);
	}
	
	private void cadastro() {
		int opcao;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Menu lanchonete");
			System.out.println("1. Continuar(Área de cadastro)");
			System.out.println("2. Realizar pedido");
			System.out.println("3. Voltar");
			opcao = scanner.nextInt();
			try {
			
				switch (opcao) {
				case 1:
					cadastroCliente();
					break;
				case 2:
					pedido();
				}
			} catch(Exception e) {
				System.out.println("Não foi possivel realizar a operação solicitada. Tente novamente.");
			}
		} while(opcao != 3);
	}
	
	private void cadastroCliente() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Nome completo:");
		String nomeCliente = scanner.nextLine();
		System.out.println("Data de nascimento(dd/mm/aaaa):");
		String dtNasc = scanner.nextLine();
		System.out.println("CPF: ");
		String cpfCliente = scanner.nextLine();
		System.out.println("E-mail: ");
		String email = scanner.nextLine();
		
		System.out.println("-----ENDEREÇO------");
		System.out.println();
		System.out.println("CEP: ");
		String cep = scanner.nextLine();
		System.out.println("Nome da rua: ");
		String rua = scanner.nextLine();
		System.out.println("Número: ");
		int numeroRes = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Bairro: ");
		String bairro = scanner.nextLine();
		System.out.println("Complemento: ");
		String complemento = scanner.nextLine();
		System.out.println("Cidade: ");
		String cidade = scanner.nextLine();
		System.out.println("Estado: ");
		String estado = scanner.nextLine();
		Endereco endereco = new Endereco(cep, numeroRes, bairro, rua, cidade, estado, complemento);
		
		//Inserindo endereço no SQL
		endereco.inserirEndereco(cep, numeroRes, bairro, rua, cidade, estado, complemento);
		
		System.out.println("-----TELEFONE------");
		System.out.println();
		System.out.println("Telefone(Não obrigatório): ");
		String telefone = scanner.nextLine();
		System.out.println("Celular(Obrigatório): ");
		String celular = scanner.nextLine();
		
		Telefone tel = new Telefone(telefone, celular);
		
		//Inserindo telefone no SQL
		tel.inserirTelefone(telefone, celular);
		System.out.println();
		System.out.println("---AUTENTICAÇÃO---");
		System.out.println();
		System.out.println("Login: ");
		String login = scanner.nextLine();
		System.out.println("Senha: ");
		String senha = scanner.nextLine();
		
		Autenticacao autenticacao = new Autenticacao(login, senha);
		
		//Inserindo o login de acesso no SQL
		autenticacao.inserirAutenticacao(login, senha);
		
		
		//Buscando as chaves primárias
		
		int cdEndereco = endereco.buscarCdEndereco();
		int cdAutenticacao = autenticacao.buscarCdAut();
		int cdTelefone = tel.buscarCdTelefone();
		
		//Construtor de cliente
		
		Cliente cliente = new Cliente(nomeCliente, cpfCliente, dtNasc, email, cdEndereco, cdAutenticacao, cdTelefone);
		
		//Inserindo o cliente no SQL
		
		cliente.inserirCliente(nomeCliente, cpfCliente, dtNasc, email, cdEndereco, cdAutenticacao, cdTelefone);
		
		
		
	}
	public String login1;
	//Inicio do pedido
	private void pedido() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("ACESSO A ÁREA DE PEDIDOS");
		System.out.println("Login: ");
		login1 = scanner.nextLine(); 
		System.out.println("Senha: ");
		String senha = scanner.nextLine();
		
		Autenticacao aut = new Autenticacao(login1, senha);
		boolean resposta = aut.consultarAutenticacao(login1, senha);
		
		if(resposta == true) {
			cardapio();
		} else {
			System.out.println("Login ou senha inválidos, tente novamente.");
			pedido();
		}  
	}
	
	
	int cdCliente;
	String produto[];
	
	private void cardapio() {
		Produto produto = new Produto();
		Pedido pedido = new Pedido();
		int opcao;
		float vlTotal = 0;
		int quantidade;
		int aux = 0;
		String observação;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("---------------------------------------------------DIGITE OS CÓDIGOS PARA EFETUAR O PEDIDO---------------------------------------------------");
		System.out.println("");
		System.out.println("1.  X-Burguer(Pão, Carne, Queijo, Presunto)--------------------------------------------------------------------------------------------R$8.00");
		System.out.println("2.  X-Salada(Pão, Carne, Queijo, Presunto, Alface, Tomate, Pepino, Milho, Ervilha, Batata Palha)---------------------------------------R$10.00");
		System.out.println("3.  X-Galinha(Pão, Frango, Queijo, Presunto, Alface, Tomate, Pepino, Milho, Ervilha, Batata Palha)-------------------------------------R$11.00");
		System.out.println("4.  X-Bacon(Pão, Carne, Queijo, Presunto, Bacon, Alface, Tomate, Pepino, Milho, Ervilha, Batata Palha)---------------------------------R$10.00");
		System.out.println("5.  X-Coração(Pão, Carne, Queijo, Presunto, Coração, Alface, Tomate, Pepino, Milho, Ervilha, Batata Palha)-----------------------------R$12.00");
		System.out.println("6.  X-Egg(Pão, Carne, Queijo, Presunto, Ovo, Alface, Tomate, Pepino, Milho, Ervilha, Batata Palha)-------------------------------------R$8.50");
		System.out.println("7.  Misto-quente(Pão, Queijo, Presunto)------------------------------------------------------------------------------------------------R$6.00");
		System.out.println("8.  X-Calabresa(Pão, Carne, Queijo, Presunto, Calabresa, Alface, Tomate, Pepino, Milho, Ervilha, Batata Palha)-------------------------R$12.00");
		System.out.println("9.  X-Casa(Pão, Carne, Queijo, Presunto, Calabresa, Bacon, Coração, Ovo, Frango, Alface, Tomate, Pepino, Milho, Ervilha, Batata Palha)-R$15.00");
		System.out.println("");
		System.out.println("--------------------------------------------------------------------BEBIDAS-------------------------------------------------------------------");
		System.out.println("");
		System.out.println("10. Água(Água mineral sem gás)---------------------------------------------------------------------------------------------------------R$1.50");
		System.out.println("11. Água(Água mineral com gás)---------------------------------------------------------------------------------------------------------R$2.50");
		System.out.println("12. Coca-Cola(Coca-Cola 350ml)---------------------------------------------------------------------------------------------------------R$3.50");
		System.out.println("13. Coca-Cola(Coca-Cola 600ml)---------------------------------------------------------------------------------------------------------R$5.50");
		System.out.println("14. Coca-Cola(Coca-Cola 1L)------------------------------------------------------------------------------------------------------------R$6.50");
		System.out.println("15. Coca-Cola(Coca-Cola 2L)------------------------------------------------------------------------------------------------------------R$8.00");
		System.out.println("16. Fanta(Fanta 350ml)-----------------------------------------------------------------------------------------------------------------R$2.50");
		System.out.println("17. Fanta(Fanta 600ml)-----------------------------------------------------------------------------------------------------------------R$5.50");
		System.out.println("18. Fanta(Fanta 2L)--------------------------------------------------------------------------------------------------------------------R$7.00");
		System.out.println("19. Sprite(Sprite 350ml)---------------------------------------------------------------------------------------------------------------R$2.50");
		System.out.println("20. Sprite(Sprite 2L)------------------------------------------------------------------------------------------------------------------R$6.50");
		System.out.println("21. Pepsi(Pespi 350ml)-----------------------------------------------------------------------------------------------------------------R$2.50");
		System.out.println("22. Pepsi(Pepsi 2L)--------------------------------------------------------------------------------------------------------------------R$5.50");
		System.out.println("23. Guaraná(Guaraná 350ml)-------------------------------------------------------------------------------------------------------------R$2.50");
		System.out.println("24. Guaraná(Guaraná 2L)----------------------------------------------------------------------------------------------------------------R$5.50");
		System.out.println("25. Budweiser(Budweiser 600ml)---------------------------------------------------------------------------------------------------------R$6.00");
		System.out.println("26. Devassa(Devassa 600ml)-------------------------------------------------------------------------------------------------------------R$5.50");
		System.out.println("27. Sub-zero(Sub-zero 600ml)-----------------------------------------------------------------------------------------------------------R$4.00");
		System.out.println("28. Brahma(Brahma 600ml)---------------------------------------------------------------------------------------------------------------R$5.00");
		System.out.println("29. Itaipava(Itaipava 600ml)-----------------------------------------------------------------------------------------------------------R$5.00");
		System.out.println("30. Skol(Skol 600ml)-------------------------------------------------------------------------------------------------------------------R$4.00");	
		
		
		do {
			System.out.println();	
			System.out.println("Continue fazendo seu pedido ou tecle 0 para finalizar...");
				opcao = scanner.nextInt();
				try {
				
					switch (opcao) {
					case 1:
						produto.buscarCardapio(1);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 2:
						produto.buscarCardapio(2);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 3:
						produto.buscarCardapio(3);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 4:
						produto.buscarCardapio(4);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 5:
						produto.buscarCardapio(5);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 6:
						produto.buscarCardapio(6);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 7:
						produto.buscarCardapio(7);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 8:
						produto.buscarCardapio(8);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 9:
						produto.buscarCardapio(9);
						System.out.println("Observação(ex: Sem milho, ervilha, tomate...): ");
						observação = scanner.nextLine();
						scanner.nextLine();
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 10:
						produto.buscarCardapio(10);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 11:
						produto.buscarCardapio(11);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 12:
						produto.buscarCardapio(12);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 13:
						produto.buscarCardapio(13);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 14:
						produto.buscarCardapio(14);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 15:
						produto.buscarCardapio(15);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 16:
						produto.buscarCardapio(16);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 17:
						produto.buscarCardapio(17);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 18:
						produto.buscarCardapio(18);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 19:
						produto.buscarCardapio(19);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 20:
						produto.buscarCardapio(20);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 21:
						produto.buscarCardapio(21);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 22:
						produto.buscarCardapio(22);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 23:
						produto.buscarCardapio(23);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 24:
						produto.buscarCardapio(24);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 25:
						produto.buscarCardapio(25);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 26:
						produto.buscarCardapio(26);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 27:
						produto.buscarCardapio(27);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 28:
						produto.buscarCardapio(28);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 29:
						produto.buscarCardapio(29);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					case 30:
						produto.buscarCardapio(30);
						System.out.println("Quantidade: ");
						quantidade = scanner.nextInt();
						aux = aux + quantidade;
						for(int i = 0; i < quantidade; i++) {
							vlTotal = (vlTotal + produto.getVlProduto());
						}
						System.out.println("Subtotal: " + vlTotal);
						break;
					}
				} catch(Exception e) {
					System.out.println("Codigo incorreto");
				}
		} while(opcao != 0);
		
		cdCliente = pedido.buscarCdCliente(login1);
		pedido.inserirPedido(cdCliente, aux, vlTotal);
		
		formaEntrega(vlTotal);
	}
	
	String nmFormaEntrega;
	float totalEntrega;
	
	public void formaEntrega(float vlTotal) {
		int opcao;
		
		FormaEntrega fE = new FormaEntrega();
		int cdPedido = fE.buscarCdPedido();
		Scanner scanner = new Scanner(System.in);
		System.out.println();
		System.out.println("FORMA DE ENTREGA");
		System.out.println("1. Retirar balcão");
		System.out.println("2. Entrega domicilio(Taxa de R$4.00 no valor total)");
		
		opcao = scanner.nextInt();
		switch(opcao) {
		case 1:
			totalEntrega = vlTotal;
			nmFormaEntrega = "Retirar balção";
			fE.inserirFormaEntrega(cdPedido, nmFormaEntrega);
			formaPagamento(totalEntrega);
			relatorioPedido();
			break;
		case 2:
			totalEntrega = vlTotal + 4;
			nmFormaEntrega = "Entrega domicilio";
			fE.inserirFormaEntrega(cdPedido, nmFormaEntrega);
			formaPagamento(totalEntrega);
			enderecoCliente();
			break;
		}
	}
	
	String nmFormaPagamento;
	float troco;
	
	public void formaPagamento(float totalEntrega) {
		int opcao, cdPedido;
		
		Scanner scanner = new Scanner(System.in);
		FormaPagamento fP = new FormaPagamento();
		cdPedido = fP.buscarCdPedido();
		System.out.println();
		System.out.println("FORMA DE PAGAMENTO");
		System.out.println("1. Dinheiro");
		System.out.println("2. Cartão(Débito/Crédito)");
		
		opcao = scanner.nextInt();
		switch(opcao) {
		case 1:
			nmFormaPagamento = "Dinheiro";
			fP.inserirFormaPagamento(nmFormaPagamento, cdPedido);
			System.out.println();
			System.out.println("Troco? 1. Sim 2. Não");
			int opTroco = scanner.nextInt();
			if(opTroco == 1) {	
				System.out.println("Troco para?(Digite o valor da nota)");
				troco = scanner.nextFloat();
				troco = troco - totalEntrega;
			}	
			break;
		case 2:
			nmFormaPagamento = "Cartão";
			fP.inserirFormaPagamento(nmFormaPagamento, cdPedido);
			break;
		}
	}
	
	public void enderecoCliente() {
		int opcao;
		Scanner scanner = new Scanner(System.in);
		System.out.println();
		System.out.println("ENDEREÇO");
		Endereco.buscarDadosEndereco(cdCliente);
		System.out.println(
				);
		System.out.println("1. Confirmar endereço");
		System.out.println("2. Alterar endereço");
		
		opcao = scanner.nextInt();
		switch(opcao) {
		case 1:
			relatorioPedido();
			break;
		case 2:
			selectEndereco();
			relatorioPedido();
		}
	}
	
	public void selectEndereco() {
		int cdEndereco = Endereco.selectEndereco(cdCliente);
		Scanner scanner = new Scanner(System.in);
		System.out.println("-----ENDEREÇO PARA ENTREGA------");
		System.out.println();
		System.out.println("CEP: ");
		String cep = scanner.nextLine();
		System.out.println("Nome da rua: ");
		String rua = scanner.nextLine();
		System.out.println("Número: ");
		int numeroRes = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Bairro: ");
		String bairro = scanner.nextLine();
		System.out.println("Complemento: ");
		String complemento = scanner.nextLine();
		System.out.println("Cidade: ");
		String cidade = scanner.nextLine();
		System.out.println("Estado: ");
		String estado = scanner.nextLine();
		
		Endereco.updateEndereco(cep, numeroRes, bairro, rua, cidade, estado, complemento, cdEndereco);
	}
	
	public void relatorioPedido() {
		int cdPedido = FormaEntrega.buscarCdPedido();
		System.out.println("**********************************************************************************************************");
		System.out.println("Seu pedido de numero: " + cdPedido + " está sendo preparado, dentro de 50 minutos estará pronto.");
		System.out.println("Forma de entrega: " + nmFormaEntrega);
		System.out.println("Forma de pagamento: " + nmFormaPagamento);
		if(nmFormaPagamento == "Dinheiro") {
			System.out.println("Troco: R$" + troco);
		}
		System.out.println("Valor total do pedido: R$" + totalEntrega);
		System.out.println();
		System.out.println("**********************************************************************************************************");
		System.out.println();
	}
	
}
