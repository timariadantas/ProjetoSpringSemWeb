package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.reposity.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d4e7f2aa";
    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        // dadosSeries.add(dados);
        repositorio.save(serie);

        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporadas> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumo.obterDados(ENDERECO + dadosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporadas dadosTemporada = conversor.obterDados(json, DadosTemporadas.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeriesBuscadas(){
        List<Serie> series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);

    }
}

//        out::println);System.out.println("Digite o nome da Série para busca");
////        var nomeSerie = leitura.nextLine();
////        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
////        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
////        System.out.println(dados);
////
////        List<DadosTemporadas> temporadas = new ArrayList<>();
////
////        for (int i = 1; i <= dados.totalTemporadas(); i++) {
////            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
////            DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
////            temporadas.add(dadosTemporadas);
////        }
////        temporadas.forEach(System.out::println);
////
//////
////////        for(int i = 0; i < dados.totalTemporadas(); i++){
////////            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodio();
////////            for(int j = 0; j< episodiosTemporada.size(); j++){
////////                System.out.println(episodiosTemporada.get(j).titulo());
////////
////////         ou
////        temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));
////
////        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
////                .flatMap(t -> t.episodio().stream())
////                .collect(Collectors.toList());
////
//////        System.out.println("\nTop 10 episódios");
//////        dadosEpisodios.stream()
//////                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//////                .peek(e-> System.out.printf("PRIMEIRO FILTRO(N/A) " +e))
//////                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//////                .peek(e-> System.out.printf("ORDENAÇÃO " +e))
//////                .limit(10)
//////                .peek(e-> System.out.printf("LIMITE " +e))
//////                .map(e -> e.titulo().toUpperCase())
//////                .peek(e-> System.out.printf("MAPEAMENTO " +e))
//////                .forEach(System.out::println);
////
////        List<Episodio> episodios = temporadas.stream()
////                .flatMap(t -> t.episodio().stream()
////                        .map(d -> new Episodio(t.numero(), d))
////                ).collect(Collectors.toList());
////
////        episodios.forEach(System.out::println);
////
////
//////        System.out.println("Digite um trecho do titulo do episodio");
//////        var trechoTitulo = leitura.nextLine();
//////        Optional<Episodio> episodioBuscado = episodios.stream()
//////                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//////                .findFirst();
//////        if (episodioBuscado.isPresent()) {
//////            System.out.println("Episodio encontrado !");
//////            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
//////        } else {
//////            System.out.println("Episódio não encontrado!");
//////        }
////
////
//////
//////        System.out.printf("A partir de que ano você deseja ver os episodios?");
//////        var ano = leitura.nextInt();
//////        leitura.nextLine();
//////
//////        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//////        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//////        episodios.stream()
//////                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//////                .forEach(e -> System.out.println(
//////                        "Temporada:" + e.getTemporada() +
//////                        "Episódio:" + e.getTitulo() +
//////                                "Data Laçamento" + e.getDataLancamento().format(formatador)
//////                ));
//////
////        Map<Integer, Double> avalicaoPorTemporada = episodios.stream()
////                .filter(e -> e.getAvaliacao() > 0.0)
////                .collect(Collectors.groupingBy(Episodio::getTemporada,
////                        Collectors.averagingDouble(Episodio::getAvaliacao)));
////        System.out.println(avalicaoPorTemporada);
////
////        DoubleSummaryStatistics est = episodios.stream()
////                .filter(e -> e.getAvaliacao() > 0.0)
////                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
////        System.out.println("Média: " +est.getAverage());
////        System.out.println("Melhor Episodio: " +est.getMax());
////        System.out.println("Pior Episodio: " +est.getMin());
////        System.out.println("Quantidade: " +est.getCount());
////    }
////}
//////
//////}
//////
//////
//////
//////
//////
////
//////        List<String> nomes = Arrays.asList("Maria", "Anderson", "Paulo", "Pedro", "Telo");
//////
//////        nomes.stream()                                      // op. intermerdiarias
//////                .sorted()                                   //ordenado
//////                .limit(3)                           // limita os 3 primeiros
//////                .filter(a ->a.startsWith("A"))             // filtar com a letra a
//////                .map(a -> a.toUpperCase())                 // transformar letra a em MAIUSCULA
//////                .forEach(System.

