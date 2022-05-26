package com.xatkit.example;

import com.xatkit.core.XatkitBot;
import com.xatkit.plugins.twilio.platform.TwilioPlatform;
import com.xatkit.plugins.twilio.platform.io.TwilioEventProvider;
import lombok.val;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;

import static com.xatkit.dsl.DSL.eventIs;
import static com.xatkit.dsl.DSL.fallbackState;
import static com.xatkit.dsl.DSL.intent;
import static com.xatkit.dsl.DSL.intentIs;
import static com.xatkit.dsl.DSL.model;
import static com.xatkit.dsl.DSL.state;
import static com.xatkit.dsl.DSL.any;
import com.xatkit.core.recognition.IntentRecognitionProviderFactory;
import com.xatkit.core.recognition.dialogflow.DialogFlowIntentRecognitionProvider;
import com.xatkit.core.recognition.dialogflow.DialogFlowConfiguration;
import com.xatkit.core.recognition.nlpjs.NlpjsIntentRecognitionProvider;
import com.xatkit.core.recognition.nlpjs.NlpjsConfiguration;

import com.xatkit.plugins.openai.platform.OpenaiPlatform;

/**
 * This is an example greetings bot designed with Xatkit.
 * <p>
 * You can check our <a href="https://github.com/xatkit-bot-platform/xatkit/wiki">wiki</a>
 * to learn more about bot creation, supported platforms, and advanced usage.
 */
public class PosgradoBot {

    /*
     * Your bot is a plain Java application: you need to define a main method to make the created jar executable.
     */
    public static void main(String[] args) {
        //OpenaiPlatform.checkOpenAIClient("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");
        
        /*
         * Define the intents our bot will react to.
         * Note that we recommend the usage of Lombok's val when using the Xatkit DSL: the fluent API defines many
         * interfaces that are not useful for bot designers. If you don't want to use val you can use our own
         * interface IntentVar instead.
         */
        val saludo = intent("saludo")
                .trainingSentence("Hola")
                .trainingSentence("Buen día")
                .trainingSentence("buenas tardes")
                .trainingSentence("que onda")
                .trainingSentence("que paso");
    
        val comoEstas = intent("comoEstas")
                .trainingSentence("Como estas?")
                .trainingSentence("que tal?")
                .trainingSentence("¿como te va?");
        
        val quienEres = intent("quienEres")
                .trainingSentence("quien eres?")
                .trainingSentence("quien es?")
                .trainingSentence("cuentame sobre ti")
                .trainingSentence("tu eres?");
        
        val comoTeHicieron = intent("comoTeHicieron")
                .trainingSentence("Como te hicieron?")
                .trainingSentence("que tecnologias utilizaron para crearte?");

        val informacionGeneral = intent("informacionGeneral")
                .trainingSentence("Información")
                .trainingSentence("Quiero informacion")
                .trainingSentence("necesito informacion")
                .trainingSentence("informacion general")
                .trainingSentence("solicito informacion");

        val informacionAcercaDePosgrado = intent("informacionAcercaDePosgrado")
                .trainingSentence("Información acerca del posgrado.")
                .trainingSentence("Que es el PCI?")
                .trainingSentence("que es el posgrado en ciencias de la informacion")
                .trainingSentence("Cuéntame del posgrado en ciencias de la información");

        val paraQueSirveElPosgrado = intent("paraQueSirveElPosgrado")
                .trainingSentence("para que sirve el posgrado.")
                .trainingSentence("de que sirve ese posgrado")
                .trainingSentence("para que se creo este posgrado");

        val perfilEgreso = intent("perfilEgreso")
                .trainingSentence("Cual es el perfil de egreso de un alumno de PROGRAMA")
                .trainingSentence("que obtengo al estudiar el PROGRAMA")
                .trainingSentence("De que me sirve estudiar el PROGRAMA")
                .trainingSentence("como sale un egresado de PROGRAMA")
                .parameter("nombrePrograma").fromFragment("PROGRAMA").entity(any());

        val duracionPosgrado = intent("duracionPosgrado")
                .trainingSentence("información sobre duración del programa de posgrado")
                .trainingSentence("cuánto tiempo dura el posgrado?")
                .trainingSentence("Me gustaría saber la duración que tiene")
                .trainingSentence("cuantos semestres son?");

        //proceso de inscripcion y requisitos 
        val requisitos = intent("requisitos")
                .trainingSentence("cuáles son los requisitos para ingresar al PROGRAMA?")
                .trainingSentence("Que necesito para entrar al PROGRAMA?")
                .trainingSentence("Cuales son los requisitos previos para ingresar al PROGRAMA?");//debes completar el proceso de preinscripcion ademas necesitaras lo siguiente:


        val procesoPreinscripcion = intent("procesoPreinscripcion")
                .trainingSentence("Información sobre proceso de preinscripción.")
                .trainingSentence("cuales son los pasos para preinscribirme")
                .trainingSentence("cuales son los pasos para pre inscribirme")
                .trainingSentence("como puedo pre inscribirme")
                .trainingSentence("como realizar la pre inscripcion");

        val recepcionDocumentos = intent("recepcionDocumentos")
                .trainingSentence("quería saber los requisitos para iniciar el proceso")
                .trainingSentence("Como puedo iniciar el proceso")
                .trainingSentence("Que tengo que hacer para entrar al posgrado")
                .trainingSentence("Quiero entrar al posgrado")
                .trainingSentence("Como entrego los documentos?")
                .trainingSentence("Como realizo la entrega de documentos de admisión")
                .trainingSentence("en que consiste el proceso de admision?");
                
        

        val procesoInscripcion = intent("procesoInscripcion")           //avisar que te tienes que preinscribir //dependiendo del desempeño cambiar los dos intents de proceso por uno solo con entidades
                .trainingSentence("Información sobre proceso de inscripción.")
                .trainingSentence("Información sobre inscripción")
                .trainingSentence("requisitos de inscripcion")
                .trainingSentence("Que tengo que hacer despues de preinscribirme")
                .trainingSentence("Cuando son las inscripciones?")
                .trainingSentence("Después de la pre inscripción qué procede?")
                .trainingSentence("como puedo inscribirme?");

        val costosProcesosBecas = intent("costosProcesosBecas")
                .trainingSentence("Informacion sobre costos")
                .trainingSentence("que mensualidad tiene")
                .trainingSentence("Cuáles son los costos de las materias o semestres?")
                .trainingSentence("cada cuanto pagan?")
                .trainingSentence("Cuanto cuesta?");

        val tienenBeca = intent("tienenBeca")
                .trainingSentence("Informacion sobre becas")
                .trainingSentence("¿La Maestría en Ciencias de la Información y el Doctorado en Ciencias de la Información, tienen becas para estudiar?")
                .trainingSentence("Tienen beca?")
                .trainingSentence("¿Cuánto tiempo dura la beca?")
                .trainingSentence("Hay algún tipo de beca para esta maestría?");
        
        val resultadosAdmision = intent("resultadosAdmision")
                .trainingSentence("Cuando obtengo los resultados de admision?")
                .trainingSentence("Como puedo saber si fui aceptada?")
                .trainingSentence("cuando avisan los resultados")
                .trainingSentence("cuando dan los resultados de admision");
        
        val informacionEntrevista = intent("informacionEntrevista")
                .trainingSentence("información sobre entrevista")
                .trainingSentence("Tengo que presentar entrevista?")
                .trainingSentence("Cuando es la entrevista?")
                .trainingSentence("En que consiste la entrevista?");
        
        val modalidadPosgrado = intent("modalidadPosgrado")
                .trainingSentence("información sobre la modalidad del posgrado")
                .trainingSentence("El formato del posgrado es en línea o presencial?")
                .trainingSentence("puedo estudiar de forma online?")
                .trainingSentence("Es de manera presencial?");
        
        val modalidadExamenes = intent("modalidadExamenes")
                .trainingSentence("información sobre la modalidad de los examenes")
                .trainingSentence("La entrevista es en linea o presencial?")
                .trainingSentence("tengo que ir a hacer la entrevista?")
                .trainingSentence("Donde se realizaran los examenes?")
                .trainingSentence("el examen es en linea?")
                .trainingSentence("Donde se realizara el examen de admision?");

        val fechaPreinscripciones = intent("fechaPreinscripciones")
                .trainingSentence("información sobre fecha de preinscripciones")
                .trainingSentence("Cual es el periodo de preinscripcion")
                .trainingSentence("Hasta cuando tengo para completar el proceso de preinscripción?")
                .trainingSentence("Hasta cuando tengo para completar el proceso de pre inscripción?");
        
        val fechaRecepcionDocumentos = intent("fechaRecepcionDocumentos") //(aclarar que es despues de preinscripción)
                .trainingSentence("información sobre fecha de Recepción de documentos")
                .trainingSentence("fecha de Recepción de documentos")
                .trainingSentence("Cuando reciben los documentos?");

        val fechaCursoPropedeuticoExamenConocimientos = intent("fechaCursoPropedeuticoExamenConocimientos")
                .trainingSentence("Cual es la fecha del Curso propedéutico y examen de conocimientos")
                .trainingSentence("cuando es el curso propedeutico")
                .trainingSentence("Cuando es el examen de conocimientos")
                .trainingSentence("Hay un examen de conocimientos?");
        
        val fechaExamenPsicométrico = intent("fechaExamenPsicométrico")
                .trainingSentence("Cual es la fecha del examen psicométrico")
                .trainingSentence("Cuando es el examen psicométrico")
                .trainingSentence("Hay un examen psicométrico?");

        val fechaExamenCeneval= intent("fechaExamenCeneval")
                .trainingSentence("Cual es la fecha del examen Ceneval EXANI III")
                .trainingSentence("Cuando es el examen Ceneval")
                .trainingSentence("tengo que hacer un examen Ceneval?");

        val inicioActividades= intent("inicioActividades")
                .trainingSentence("Cual es la fecha de inicio de actividades")
                .trainingSentence("cuando entran los alumnos?")
                .trainingSentence("Cuando comienza el programa")
                .trainingSentence("cuando empiezan las clases?");
        
        val frecuenciaConvocatoria= intent("frecuenciaConvocatoria")
                .trainingSentence("Información frecuencia de la convocatoria?")
                .trainingSentence("Cada cuánto se abre la convocatoria?")
                .trainingSentence("despues de este periodo no podre volver a inscribirme?");

        val informacionMovilidad= intent("informacionMovilidad")
                .trainingSentence("Información sobre movilidad")
                .trainingSentence("Se puede realizar alguna movilidad durante la maestría?")
                .trainingSentence("existe la posibilidad de estudiar en el extranjero?");        
        
        val lineasInvestigacion= intent("lineasInvestigacion")
                .trainingSentence("Cuales son las lineas de investigacion")
                .trainingSentence("Que son las LGAC");
                

        val planEstudios  = intent("planEstudios")
                .trainingSentence("Cual es el plan de estudios del MDPROGRAMA")
                .trainingSentence("Cual es el plan de estudios del programa de MDPROGRAMA")
                .parameter("nombrePrograma").fromFragment("MDPROGRAMA").entity(any());
        
        val docentes   = intent("docentes")
                .trainingSentence("información sobre los docentes")
                .trainingSentence("Puedes darme la lista de profesores?")
                .trainingSentence("Quienes son los profesores?");

        val informacionExamenes = intent("informacionExamenes")
                .trainingSentence("información sobre los Examenes")
                .trainingSentence("Tengo que hacer examen?")
                .trainingSentence("tienen examen de admision?")
                .trainingSentence("debo realizar algun examen de admision?");
        
        val datosDeContacto = intent("datosDeContacto")
                .trainingSentence("Datos de Contacto")
                .trainingSentence("Tienen numero de telefono?")
                .trainingSentence("si tengo una duda con quien puedo comunicarme?")
                .trainingSentence("puedes darme el numero de telefono?")
                .trainingSentence("puedes comunicarme con un humano?")
                .trainingSentence("necesito el correo electronico")
                .trainingSentence("la institucion tiene celular?")
                .trainingSentence("Tienen Correo electronico?")
                .trainingSentence("tienen pagina de facebook?")
                .trainingSentence("Tienen pagina web?")
                .trainingSentence("donde puedo conseguir mas informacion?");
        
        val direccion = intent("direccion")
                .trainingSentence("Donde estan ubicados?")
                .trainingSentence("necesito la direccion?")
                .trainingSentence("Donde esta el posgrado?")
                .trainingSentence("Donde esta ubicado el posgrado?");

        val guiasExamen = intent("guiasExamen")
                .trainingSentence("tienes una guia del examen?")
                .trainingSentence("que vendra en los examenes de maestria?")
                .trainingSentence("que vendra en los examenes de doctorado?")
                .trainingSentence("Donde puedo obtener una guia de los examenes?")
                .trainingSentence("que vendra en los examenes de admision?");
        /*
        val obtenerConvocatoria = intent("obtenerConvocatoria")
                .trainingSentence("quiero obtener convocatoria")
                .trainingSentence("puedes enviarme la imagen de la convocatoria?");*/

        val ofrecerAyuda = intent("ofrecerAyuda")
                .trainingSentence("gracias")
                .trainingSentence("De acuerdo, gracias por la información")
                .trainingSentence("Perfecto")
                .trainingSentence("Ok")
                .trainingSentence("si")
                .trainingSentence("bien");
        
        val respuestaNegativa = intent("respuestaNegativa")
                .trainingSentence("no gracias")
                .trainingSentence("no")
                .trainingSentence("negativo")
                .trainingSentence("nop")
                .trainingSentence("nel");

        /*
         * Instantiate the platform we will use in the bot definition.
         * <p>
         * Instantiating the platform before specifying the bot's states creates a usable reference that can be
         * accessed in the states, e.g:
         * <pre>
         * {@code
         * myState
         *   .body(context -> reactPlatform.reply(context, "Hi, nice to meet you!");
         * }
         * </pre>
         */
        /*
         * Similarly, instantiate the intent/event providers we want to use.
         * <p>
         * In our example we want to receive intents (i.e. interpreted user inputs) from our react client, so we
         * create a ReactIntentProvider instance. We also want to receive events from the react client (e.g. when the
         * client's connection is ready), so we create a ReactEventProvider instance.
         * <p>
         * We can instantiate as many providers as we want, including providers from different platforms.
         */
        
        TwilioPlatform twilioPlatform = new TwilioPlatform();
        TwilioEventProvider twilioEventProvider = new TwilioEventProvider(twilioPlatform);


        /*
         * Create the states we want to use in our bot.
         * <p>
         * Similarly to platform/provider creation, we create the state variables first, and we specify their content
         * later. This allows to define circular references between states (e.g. AwaitingQuestion -> HandleWelcome ->
         * AwaitingQuestion).
         * <p>
         * This is not mandatory though, the important point is to have fully specified states when we build the
         * final bot model.
         */
        val init = state("Init");
        val estadoEspera = state("EstadoEspera");
        val estadoSaludo = state("EstadoSaludo");
        val estadoComoestas = state("EstadoComoestas");
        val quienSoy = state("quienSoy");
        val handlePerfilEgreso = state("HandlePerfilEgreso");
        val handleInformacionGeneral = state("handleInformacionGeneral");
        val handleInformacionAcercaDePosgrado = state("handleInformacionAcercaDePosgrado");
        val handleParaQueSirveElPosgrado = state("HandleParaQueSirveElPosgrado");
        val handleDuracionPosgrado = state("HandleDuracionPosgrado");
        val handleRequisitos = state("handleRequisitos");
        val handleProcesoPreinscripcion = state("handleProcesoPreinscripcion");
        val handleProcesoPreinscripcionPasos = state("handleProcesoPreinscripcionPasos");
        val handleProcesoPreinscripcionPasos2 = state("handleProcesoPreinscripcionPasos2");
        val handleProcesoInscripcion = state("handleProcesoInscripcion");
        val handleCostosProcesosBecas = state("handleCostosProcesosBecas");
        val handleResultadosAdmision = state("handleResultadosAdmision");
        val handleInformacionEntrevista = state("handleInformacionEntrevista");
        val handleModalidadPosgrado = state("handleModalidadPosgrado"); 
        val handleFechaPreinscripciones = state("handleFechaPreinscripciones");
        val handleFechaRecepcionDocumentos = state("handleFechaRecepcionDocumentos");
        val handleFechaCursoPropedeuticoExamenConocimientos = state("handleFechaCursoPropedeuticoExamenConocimientos");
        val handleFechaExamenPsicométrico = state("handleFechaExamenPsicométrico");
        val handleFechaExamenCeneval = state("handleFechaExamenCeneval");
        val handleInicioActividades = state("handleInicioActividades");
        val handleFrecuenciaConvocatoria = state("handleFrecuenciaConvocatoria");        
        val handleInformacionMovilidad = state("handleInformacionMovilidad"); 
        val handleLineasInvestigacion = state("handleLineasInvestigacion"); 
        val handlePlanEstudios = state("handlePlanEstudios"); 
        val handleDocentes = state("handleDocentes"); 
        val handleOfrecerAyuda = state("handleOfrecerAyuda"); 
        val handleInformacionExamenes = state("handleInformacionExamenes");
        val handleDatosDeContacto = state("handleDatosDeContacto");
        val handleModalidadExamenes = state("handleModalidadExamenes");
        val handleGuiasExamen = state("handleGuiasExamen");
        val handleDireccion = state("handleDireccion");
        val handleRecepcionDocumentos = state("handleRecepcionDocumentos");
        val handleTienenBeca = state("handleTienenBeca");
        val entiendo = state("entiendo");
        
        /*
         * Specify the content of the bot states (i.e. the behavior of the bot).
         * <p>
         * Each state contains:
         * <ul>
         * <li>An optional body executed when entering the state. This body is provided as a lambda expression
         * with a context parameter representing the current state of the bot.</li>
         * <li>A mandatory list of next() transitions that are evaluated when a new event is received. This list
         * must contain at least one transition. Transitions can be guarded with a when(...) clause, or
         * automatically navigated using a direct moveTo(state) clause.</li>
         * <li>An optional fallback executed when there is no navigable transition matching the received event. As
         * for the body the state fallback is provided as a lambda expression with a context parameter representing
         * the current state of the bot. If there is no fallback defined for a state the bot's default fallback state
         * is executed instead.
         * </li>
         * </ul>
         */
        init
                .next()
                .moveTo(estadoEspera);

        estadoEspera
                .next()
                

                /*
                 * The Xatkit DSL offers dedicated predicates (intentIs(IntentDefinition) and eventIs
                 * (EventDefinition) to check received intents/events.
                 * <p>
                 * You can also check a condition over the underlying bot state using the following syntax:
                 * <pre>
                 * {@code
                 * .when(context -> [condition manipulating the context]).moveTo(state);
                 * }
                 * </pre>
                 */
                .when(intentIs(saludo)).moveTo(estadoSaludo)
                .when(intentIs(comoEstas)).moveTo(estadoComoestas)
                .when(intentIs(quienEres)).moveTo(quienSoy)
                .when(intentIs(recepcionDocumentos)).moveTo(handleRecepcionDocumentos)
                .when(intentIs(perfilEgreso)).moveTo(handlePerfilEgreso)
                .when(intentIs(informacionGeneral)).moveTo(handleInformacionGeneral) 
                .when(intentIs(informacionAcercaDePosgrado)).moveTo(handleInformacionAcercaDePosgrado)
                .when(intentIs(paraQueSirveElPosgrado)).moveTo(handleParaQueSirveElPosgrado)
                .when(intentIs(duracionPosgrado)).moveTo(handleDuracionPosgrado) 
                .when(intentIs(requisitos)).moveTo(handleRequisitos)
                .when(intentIs(procesoPreinscripcion)).moveTo(handleProcesoPreinscripcion)
                .when(intentIs(procesoInscripcion)).moveTo(handleProcesoInscripcion)
                .when(intentIs(costosProcesosBecas)).moveTo(handleCostosProcesosBecas) 
                .when(intentIs(resultadosAdmision)).moveTo(handleResultadosAdmision) 
                .when(intentIs(informacionEntrevista)).moveTo(handleInformacionEntrevista)
                .when(intentIs(modalidadPosgrado)).moveTo(handleModalidadPosgrado)
                .when(intentIs(fechaPreinscripciones)).moveTo(handleFechaPreinscripciones)
                .when(intentIs(fechaRecepcionDocumentos)).moveTo(handleFechaRecepcionDocumentos)
                .when(intentIs(fechaCursoPropedeuticoExamenConocimientos)).moveTo(handleFechaCursoPropedeuticoExamenConocimientos)
                .when(intentIs(fechaExamenPsicométrico)).moveTo(handleFechaExamenPsicométrico)
                .when(intentIs(fechaExamenCeneval)).moveTo(handleFechaExamenCeneval)  
                .when(intentIs(inicioActividades)).moveTo(handleInicioActividades)
                .when(intentIs(frecuenciaConvocatoria)).moveTo(handleFrecuenciaConvocatoria) 
                .when(intentIs(informacionMovilidad)).moveTo(handleInformacionMovilidad)
                .when(intentIs(lineasInvestigacion)).moveTo(handleLineasInvestigacion)
                .when(intentIs(planEstudios)).moveTo(handlePlanEstudios)
                .when(intentIs(docentes)).moveTo(handleDocentes)
                .when(intentIs(ofrecerAyuda)).moveTo(handleOfrecerAyuda)
                .when(intentIs(informacionExamenes)).moveTo(handleInformacionExamenes)
                .when(intentIs(datosDeContacto)).moveTo(handleDatosDeContacto)
                .when(intentIs(direccion)).moveTo(handleDireccion)
                .when(intentIs(modalidadExamenes)).moveTo(handleModalidadExamenes)
                .when(intentIs(guiasExamen)).moveTo(handleGuiasExamen)
                .when(intentIs(tienenBeca)).moveTo(handleTienenBeca);
        estadoSaludo
                .body(context -> twilioPlatform.replyMedia(context,"Buen día 😄, mi nombre es Angel y puedo asistirte con información acerca del posgrado en ciencias de información, ¿en que puedo ayudarte hoy?","https://raw.githubusercontent.com/AngelTM/assetsPosgradoBot/4389dda46bd9843f8293c0031858b9863c1c79b3/Convocatoria-PCI-2022.jpg"))
                .next()
                /*
                 * A transition that is automatically navigated: in this case once we have answered the user we
                 * want to go back in a state where we wait for the next intent.
                 */
                .moveTo(estadoEspera);

        estadoComoestas
                .body(context -> twilioPlatform.reply(context, "Estoy bien, gracias si tienes alguna duda por favor házmela saber"))
                .next()
                .moveTo(estadoEspera);
        
        quienSoy
                .body(context -> twilioPlatform.reply(context, "Soy un asistente virtual creado por un alumno del posgrado en ciencias de la información y estoy aquí para ayudarte con las dudas que tengas acerca del PCI"))
                .next()
                .moveTo(estadoEspera);
        

        handlePerfilEgreso
                .body(context -> {
                        String programa = (String) context.getIntent().getValue("nombrePrograma");
                        if((programa.contains("m")||programa.contains("M"))&&(!programa.contains("d")|| !programa.contains("D")) ){
                                twilioPlatform.reply(context, "El egresado del programa de maestría tendrá dominio sobre las teorías, metodologías y tecnologías de las ciencias de la información, del contexto actual, estructura y desarrollo tecnológico del sector productivo; tendrá la habilidad de, con buena calidad, redactar y presentar informes científicos resultado de su trabajo, así como interpretar correctamente información científica en idioma inglés.\n \n"+
                                "Poseerá la habilidad de aplicar la metodología de la investigación científica en un proyecto determinado, generando resultados de investigación original, al menos a nivel de iniciación; de la operación eficiente y manejo de equipo, materiales, instrumentos y equipos de laboratorio afines a las Ciencias de la Información; de crear nuevas técnicas y procedimientos de operación de materiales, equipos e instrumentos de laboratorios.\n \n"+
                                "Puedes leer mas sobre esto en el sitio web del posgrado: https://pci.uas.edu.mx/pci/maestria/ en el apartado de Perfil de Egreso.");
                        }else if((programa.contains("d")||programa.contains("D"))){
                                
                                twilioPlatform.reply(context, "El egresado desarrollará las siguientes competencias: \n \n"+
                                "Capacidad en la aplicación de la metodología de la investigación científica.\n \n"+
                                "Completo dominio de las teorías, metodologías y tecnologías de las ciencias de la información.\n \n"+
                                "Conocimiento del contexto actual, estructura y desarrollo tecnológico del sector productivo.\n \n"+
                                "Capacidad en la generación de investigación básica y aplicada original.\n \n"+
                                "Capacidad para redactar y presentar informes científicos de sus resultados en idioma inglés.\n \n"+
                                "Dominio eficiente de la operación y manejo de equipo, materiales, instrumentos y equipos que constituyen las tecnologías de información y comunicaciones.\n \n"+
                                "Estará  capacitado  para  la  creación  de  nuevas  técnicas  y  procedimientos  de operación de materiales, equipos e instrumentos de laboratorios.\n \n"+
                                "Estará capacitado para guiar y producir recurso humano en grupos de aprendizaje de educación superior y posgrado.");
                        }else{
                                twilioPlatform.reply(context, "Maestría: \n \n"+
                                "El egresado tendrá dominio sobre las teorías, metodologías y tecnologías de las ciencias de la información, del contexto actual, estructura y desarrollo tecnológico del sector productivo; tendrá la habilidad de, con buena calidad, redactar y presentar informes científicos resultado de su trabajo, así como interpretar correctamente información científica en idioma inglés.\n \n"+
                                "Puedes leer más sobre esto en el sitio web del posgrado: https://pci.uas.edu.mx/pci/maestria/ en el apartado de Perfil de Egreso o preguntarme directamente por el perfil de egresado de maestría.\n \n"+
                                
                                "Doctorado: \n \n"+
                                "El egresado desarrollará las siguientes competencias: \n \n"+
                                "Capacidad en la aplicación de la metodología de la investigación científica.\n \n"+
                                "Completo dominio de las teorías, metodologías y tecnologías de las ciencias de la información.\n \n"+
                                "Conocimiento del contexto actual, estructura y desarrollo tecnológico del sector productivo.\n \n"+
                                "Capacidad en la generación de investigación básica y aplicada original.\n \n"+
                                "Capacidad para redactar y presentar informes científicos de sus resultados en idioma inglés.\n \n"+
                                "Dominio eficiente de la operación y manejo de equipo, materiales, instrumentos y equipos que constituyen las tecnologías de información y comunicaciones.\n \n"+
                                "Estará capacitado  para  la  creación  de  nuevas  técnicas  y  procedimientos  de operación de materiales, equipos e instrumentos de laboratorios.\n \n"+
                                "Estará capacitado para guiar y producir recurso humano en grupos de aprendizaje de educación superior y posgrado."
                                );
                        }
                })
                .next()
                .moveTo(estadoEspera);
                
        handleInformacionGeneral
                .body(context -> twilioPlatform.reply(context, " ¿Claro qué información necesitas? \n \n"+
                "Por ejemplo, puedo ayudarte a resolver las siguientes preguntas: \n \n"+
                "¿Qué es el posgrado en ciencias de la información? \n"+
                "¿cuánto tiempo dura el posgrado? \n"+
                "¿Cuándo obtengo los resultados de admisión?"))
                .next()
                .moveTo(estadoEspera);        
                
        handleInformacionAcercaDePosgrado
                .body(context -> twilioPlatform.reply(context, "Actualmente, en la Universidad Autónoma de Sinaloa, el Posgrado en Ciencias de la Información oferta el programa de Maestría y Doctorado en Ciencias de la Información, los cuales tiene como eje central la formación de estudiantes con conocimientos amplios y sólidos en las líneas de generación y aplicación del conocimiento (LGAC) en “Computación y Sistemas” y “Geomática y Geodesia” con una alta capacidad para el ejercicio profesional.  si te interesa puedes leer más al respecto en los siguientes enlaces: https://pci.uas.edu.mx/pci/ https://pci.uas.edu.mx/mensaje-de-bienvenida/"))
                .next()
                .moveTo(estadoEspera); 

        handleParaQueSirveElPosgrado
                .body(context -> twilioPlatform.reply(context, "Con base en la aportación científica, social e innovadora de este Posgrado, se postula un programa con gran pertinencia en investigación y desarrollo, al contar con las Líneas de Generación y Aplicación del Conocimiento que permitirán, desarrollar propuestas científicas para atender las problemáticas de la sociedad, en especial, las que hoy son prioritarias para el país, además de incursionar de manera eficaz y eficiente en la implementación de estrategias en Ciencias de la Información que se ocupa de aspectos relacionados con la producción, recolección, organización, difusión, recuperación y optimización de la información."))
                .next()
                .moveTo(estadoEspera);
                
        handleDuracionPosgrado
                .body(context -> twilioPlatform.reply(context, "La duración del programa consta de 24 meses para maestría y 48 meses para doctorado"))
                .next()
                .moveTo(estadoEspera);  
        
        handleRequisitos
                .body(context -> { 
                        String programa = (String) context.getIntent().getValue("nombrePrograma");
                        if((programa.contains("m")||programa.contains("M"))&&(!programa.contains("d")|| !programa.contains("D")) ){
                                twilioPlatform.reply(context, "• Solicitud de admisión. Formato: https://pci.uas.edu.mx/wp-content/uploads/2022/03/Solicitud_de_Ingreso.pdf \n \n"+
                                "• Carta de intención.\n \n"+
                                "• Currículum vitae con documentos probatorios.\n \n"+
                                "• Presentar dos cartas de recomendación de académicos. Formato: https://pci.uas.edu.mx/wp-content/uploads/2022/04/Carta-de-recomendacion.pdf\n \n"+
                                "• Carta de compromiso de dedicación exclusiva.\n \n"+
                                "• Entrevistarse con la comisión de ingreso, y mostrar interés por alguna de la líneas de investigación que el programa ofrece.\n \n"+
                                "• Presentar examen en TOEFL ITP.\n \n"+
                                "• Presentar examen CENEVAL EXANI III.\n \n"+
                                "• Aprobar el examen general de conocimiento.\n \n"+
                                "• Evaluación Conocimientos Básicos https://pci.uas.edu.mx/wp-content/uploads/2022/03/Evaluacion_Conocimientos_Basicos.pdf \n \n"+
                                "• Evaluación Conocimientos en Ciencias de la Informática https://pci.uas.edu.mx/wp-content/uploads/2022/03/Evaluacion-Conocimientos-en-Ciencias-de-la-Informacion.pdf \n \n"+
                                "• Egresado de licenciatura afín a este programa.\n \n"+
                                "• Certificado de estudios de licenciatura, con promedio mínimo de ocho.\n \n"+
                                "• Identificación oficial con fotografía.\n \n"+
                                "• Presentar título de Licenciatura.\n \n"+
                                "• En caso de no contar con el título, presentar documento probatorio de trámite.\n \n");
                        }else if((programa.contains("d")||programa.contains("D"))){
                                
                                twilioPlatform.reply(context, "• Presentar solicitud de admisión. https://pci.uas.edu.mx/wp-content/uploads/2022/03/Solicitud_de_Ingreso.pdf \n \n"+ 
                                "• Identificación oficial con fotografía.\n \n"+
                                "• Presentar una propuesta de investigación de acuerdo a las líneas de investigación consideradas en el Programa de Doctorado: https://pci.uas.edu.mx/wp-content/uploads/2022/03/Guia-Protocolo.pdf\n \n"+
                                "• Presentar el examen Examen TOEFL ITP a criterio del H. Comité Académico de Admisión.\n \n"+
                                "• Presentar Examen Ceneval Exani III.\n \n"+
                                "• Presentar título de Maestría.\n \n"+
                                "• Aprobar el examen general de conocimiento\n \n"+
                                "• Computación y Sistemas: https://pci.uas.edu.mx/wp-content/uploads/2022/03/opcion-terminal-de-computacion-y-sistemas.pdf\n \n"+
                                "• Geodesia y Geomática: https://pci.uas.edu.mx/wp-content/uploads/2022/03/opcion-terminal-de-geomatica.pdf \n \n"+
                                "• Carta de presentación personal en la que se explique el interés que motiva su ingreso al programa.\n \n"+
                                "• Certificado de Maestría con un promedio mínimo de 8, en una escala de 0 al 10.\n \n"+
                                "• Currículum Vitae acompañado de documentos probatorios.\n \n"+
                                "• Dos cartas de recomendación académica de su Institución de origen. https://pci.uas.edu.mx/wp-content/uploads/2022/03/Carta-de-recomendacion.pdf \n \n"+
                                "• Someterse a un proceso de admisión (exámenes y entrevistas) que defina su ingreso o no al Programa de Doctorado en Ciencias de la Información.\n \n"+
                                "• Aprobar examen del idioma español en el Centro de Idiomas de las UAS, cuando el aspirante proceda de un país en el que no sea dominante de ese idioma.\n \n"+
                                "• Entrevista con miembros del H. Comité Académico de Admisión.\n \n"+
                                "• Presentar carta compromiso de dedicación de tiempo completo a sus estudios.");
                        }else{
                                twilioPlatform.reply(context, "Requisitos generales maestría:\n \n"+
                                "• Solicitud de admisión. Formato: https://pci.uas.edu.mx/wp-content/uploads/2022/03/Solicitud_de_Ingreso.pdf \n \n"+
                                "• Carta de intención.\n \n"+
                                "• Currículum vitae con documentos probatorios.\n \n"+
                                "• Presentar dos cartas de recomendación de académicos. Formato: https://pci.uas.edu.mx/wp-content/uploads/2022/04/Carta-de-recomendacion.pdf\n \n"+
                                "• Carta de compromiso de dedicación exclusiva.\n \n"+
                                "• Entrevistarse con la comisión de ingreso, y mostrar interés por alguna de la líneas de investigación que el programa ofrece.\n \n"+
                                "• Presentar examen en TOEFL ITP.\n \n"+
                                "• Presentar examen CENEVAL EXANI III.\n \n"+
                                "• Aprobar el examen general de conocimiento.\n \n"+
                                "• Evaluación Conocimientos Básicos https://pci.uas.edu.mx/wp-content/uploads/2022/03/Evaluacion_Conocimientos_Basicos.pdf \n \n"+
                                "• Evaluación Conocimientos en Ciencias de la Informática https://pci.uas.edu.mx/wp-content/uploads/2022/03/Evaluacion-Conocimientos-en-Ciencias-de-la-Informacion.pdf \n \n"+
                                "• Egresado de licenciatura afín a este programa.\n \n"+
                                "• Certificado de estudios de licenciatura, con promedio mínimo de ocho.\n \n"+
                                "• Identificación oficial con fotografía.\n \n"+
                                "• Presentar título de Licenciatura.\n \n"+
                                "• En caso de no contar con el título, presentar documento probatorio de trámite.\n \n");
                        }
                })
                .next()
                .moveTo(estadoEspera);
        
        handleProcesoPreinscripcion
                .body(context -> twilioPlatform.reply(context, "Despues de la primera *entrega de documentos de admisión*  necesitas hacer es llevar a cabo tu proceso de preinscripción el cual puedes encontrar completo en el portal https://pci.uas.edu.mx/proceso-de-admision/ en el apartado proceso de preinscripción. \n \n"+
                "*deseas que te muestre la lista completa de pasos para preinscripción?* "))
                .next()
                        .when(intentIs(ofrecerAyuda)).moveTo(handleProcesoPreinscripcionPasos)
                        .when(intentIs(respuestaNegativa)).moveTo(entiendo)
                        .fallback(context -> twilioPlatform.reply(context, "por favor responde si quieres que te muestre o no los pasos de preinscripción con un *si* o *no*"));
                        
        handleProcesoPreinscripcionPasos
                .body(context -> twilioPlatform.reply(context, "para realizar el proceso de preinscripción necesitas seguir los siguientes pasos:\n\n"+
                        "1- ingresar al siguiente enlace: http://siia.uasnet.mx/preinscripcion/paso1a.asp\n \n"+
                        "2- En la opción PRIMER INGRESO llenar el formulario con tus datos y seleccionar el nivel académico (Maestría o Doctorado). En la misma ventana selecciona la localidad donde está ubicado el Posgrado, la Unidad Académica y el Programa Educativo a cursar.\n\n"+
                        "3- asegúrate de llenar todos tus datos y hacer click en la pestaña “acepto términos y condiciones”. Es requisito indispensable proporciones estos datos y aceptes te envíen mensajes por correo electrónico y/o teléfono, ya que sólo mediante esta vía se te informará el número de ficha de preinscripción y contraseña, con las cuales podrás continuar con el proceso. Al terminar de llenar toda la información haz clic en REGISTRAR, con lo cual el sistema generará un número de control con los datos proporcionados, que debes conservar para cualquier aclaración durante el proceso.\n\n"+
                        "4- En el correo y/o mensaje que recibirás, se indica el número de ficha de preinscripción y la clave con los cuales puedes ingresar de nueva cuenta a la página de preinscripción y en la opción CONTINUAR imprimirás la hoja de pago de preinscripción, así como tu ficha de preinscripción\n \n."
                        ))
                .next()
                .moveTo(handleProcesoPreinscripcionPasos2);

        handleProcesoPreinscripcionPasos2
                .body(context -> twilioPlatform.reply(context,"5- Realiza el pago en alguno de los lugares que se indican en la hoja de pago.\n \n"+
                        "6- La ficha de preinscripción contiene un número y clave de acceso para que ingreses al portal de admisión http://dse.uasnet.mx/admision ingresar número de ficha y clave.\n \n"+
                        "7- Llenar la solicitud de preinscripción (con mayúsculas, sin acentos, sin comas. no abreviar, no omitir ningún nombre, ni apellido)."+
                        "8- Imprimir 2 veces la solicitud de preinscripción.\n \n"+
                        "9- Imprimir el recibo de pago.\n \n"+
                        "10- Pagar en banco Banorte o a banco Santander.\n \n"+
                        "11- A las 24 horas de haber hecho el pago, volver a entrar a la página http://dse.uasnet.mx/admision para llenar el formulario del ceneval. (Llenar en su totalidad y sin errores ortográficos).\n \n"+
                        "12- Imprimir dos veces el pase de ingreso al examen ceneval.\n \n"+
                        "13- Entregar la siguiente documentación para la preinscripción en control escolar de la Facultad de Informática Culiacán (FIC):\n \n"+
                        "Copia del acta de nacimiento, Copia del certificado de Maestría para Doctorado, CURP impresa de internet, Solicitud de preinscripción, Recibo pagado.\n \n"+
                        "14. Al entregar la documentación se le tomará la foto y se le entregará la constancia de preinscripción.\n \n"))
                .next()
                .moveTo(estadoEspera);
        
                handleProcesoInscripcion
                        .body(context -> twilioPlatform.reply(context, "Para llevar a cabo la inscripción es necesario realizar el proceso de *Preinscripción* del 06 al 08 de junio de 2022, los exámenes de admisión y la Entrevista después de lo cual se emitirán los resultados de las evaluaciones y en caso de ser admitido podrás inscribirte Del 15 al 19 de agosto de 2022."))
                        .next()
                        .moveTo(estadoEspera);

                handleCostosProcesosBecas
                        .body(context -> twilioPlatform.reply(context, "Para información de Costos y procesos de Becas, favor de comunicarse con el Departamento de Coordinación del Posgrado en Ciencias de la Información al correo electrónico coordinacionpci@uas.edu.mx "))
                        .next()
                        .moveTo(estadoEspera); 
                
                handleResultadosAdmision
                        .body(context -> twilioPlatform.reply(context, "Los resultados de Admisión son emitidos el 15 de julio de 2022. por medio de correo electrónico"))
                        .next()
                        .moveTo(estadoEspera);
                        
                handleInformacionEntrevista
                        .body(context -> twilioPlatform.reply(context, "Uno de los requisitos para entrar al programa consiste en una entrevista que será llevada a cabo en el periodo del 11 al 14 de julio del 2022. En este proceso se te harán preguntas relacionadas a tu experiencia académica, profesional e intereses. No es algo por lo que preocuparse solo asegúrate de contestar con sinceridad e investiga previamente las líneas de investigación que el posgrado ofrece 😃"))
                        .next()
                        .moveTo(estadoEspera); 
                        
                handleModalidadPosgrado
                        .body(context -> twilioPlatform.reply(context, "Por el momento los 2 programas de posgrado son ofrecidos solo de manera presencial."))
                        .next()
                        .moveTo(estadoEspera); 
                        
                handleFechaPreinscripciones
                        .body(context -> twilioPlatform.reply(context, "Las preinscripciones serán realizadas en el periodo Del 06 al 08 de junio de 2022."))
                        .next()
                        .moveTo(estadoEspera); 
                        
                handleFechaRecepcionDocumentos
                        .body(context -> twilioPlatform.reply(context, "La recepción de documentos esta programada Del 22 de marzo al 06 de junio de 2022, recuerda que debes realizar previamente el proceso de preinscripción antes del 08 de junio de 2022."))
                        .next()
                        .moveTo(estadoEspera);
                        
                handleFechaCursoPropedeuticoExamenConocimientos
                        .body(context -> twilioPlatform.reply(context, "Curso propedéutico y examen de conocimientos serán realizados en línea Del 13 al 24 de junio de 2022."))
                        .next()
                        .moveTo(estadoEspera);
                
                handleFechaExamenPsicométrico
                        .body(context -> twilioPlatform.reply(context, "El examen psicométrico esta programado para el 29 de junio de 2022"))
                        .next()
                        .moveTo(estadoEspera);

                handleFechaExamenCeneval
                        .body(context -> twilioPlatform.reply(context, "El examen Ceneval EXANI III esta programado el 01 de julio de 2022"))
                        .next()
                        .moveTo(estadoEspera);
                
                handleInicioActividades
                        .body(context -> twilioPlatform.reply(context, "El inicio de actividades es el 05 de septiembre de 2022."))
                        .next()
                        .moveTo(estadoEspera);
                
                handleFrecuenciaConvocatoria 
                        .body(context -> twilioPlatform.reply(context, "Actualmente la convocatoria de maestría está disponible una vez al año comenzando con el ciclo escolar de la universidad autónoma de Sinaloa.\n \n"+
                        "En caso de doctorado la oferta es semestral, por lo que un alumno puede aspirar a ingresar al programa 2 veces al año.\n \n"+   
                        "El próximo periodo de preinscripciones abarca del 06 al 08 de junio de 2022."))
                        .next()
                        .moveTo(estadoEspera);

                handleInformacionMovilidad
                        .body(context -> twilioPlatform.replyMedia(context,"Actualmente existe la posibilidad de acceder a un Programa de Beca de Movilidad, estancias nacionales e internacionales y otro tipo de colaboraciones para estudiantes de posgrado. Para más información puedes comunicarte con movilidadestudiantil@uas.edu.mx y coordinacionpci@uas.edu.mx","https://raw.githubusercontent.com/AngelTM/assetsPosgradoBot/bcaa664d07e7bc47f4371ab4c78de6eea3df1c70/movilidadUas.png"))
                        .next()
                        .moveTo(estadoEspera);

                handleLineasInvestigacion
                        .body(context -> twilioPlatform.reply(context,"El programa de Maestría y Doctorado en Ciencias de la Información es multi – disciplinario en su naturaleza y se contempla que se agreguen líneas de investigación conforme las distintas disciplinas del conocimiento que se cultivan en nuestra universidad y que se consideren necesarias formar recursos humanos con especialización en las ciencias de la información.\n \n"+
                        "Estas líneas de investigación se agrupan en opciones o salidas terminales. La Maestría y Doctorado en Ciencias de la Información incorpora dos opciones terminales; *Computación y Sistemas*; y *Geomática*. puedes ver en que consiste cada una en el siguiente enlace: https://pci.uas.edu.mx/pci/lgac/ "))
                        .next()
                        .moveTo(estadoEspera);
                
                handlePlanEstudios
                        .body(context ->{
                                twilioPlatform.replyMedia(context,"Mapa Curricular Maestría","https://raw.githubusercontent.com/AngelTM/assetsPosgradoBot/bcaa664d07e7bc47f4371ab4c78de6eea3df1c70/MapaCurricular_Maestria.jpg");
                                twilioPlatform.replyMedia(context,"Mapa Curricular Doctorado ","https://raw.githubusercontent.com/AngelTM/assetsPosgradoBot/bcaa664d07e7bc47f4371ab4c78de6eea3df1c70/MapaCurricular_Doctorado.jpg");
                        })
                        .next()
                        .moveTo(estadoEspera);
                
                handleDocentes
                        .body(context -> twilioPlatform.reply(context, "Puedes encontrar la lista completa de profesores de tiempo completo adscritos al programa aquí: https://pci.uas.edu.mx/profesores-de-tiempo-completo-adscritos-al-programa/ \n \n"+
                        "Y los profesores de tiempo parcial aquí: https://pci.uas.edu.mx/profesores-de-tiempo-parcial-adscritos-al-programa/"))
                        .next()
                        .moveTo(estadoEspera);
                
                handleInformacionExamenes
                        .body(context -> twilioPlatform.reply(context, "Para ingresar al programa es necesario que realices los siguientes exámenes:\n \n"+
                        "• examen de conocimientos \n \n"+
                        "• Examen psicométrico \n \n"+
                        "• Examen Ceneval EXANI III\n \n"+
                        "Además, se realizará una entrevista, todos estos procedimientos son llevados a cabo de manera remota."))
                        .next()
                        .moveTo(estadoEspera);

                handleDatosDeContacto
                        .body(context -> twilioPlatform.reply(context, "Claro, estos son los datos de contacto del PCI:\n \n "+
                        "Teléfonos: 667 716-13-61 y 667 716-11-49\n \n "+
                        "Correo electrónico: coordinacionpci@uas.edu.mx \n \n "+
                        "Facebook: Posgrado en Ciencias de la Información https://www.facebook.com/PCI.UAS/?fref=ts \n \n "+
                        "Correo Electrónico de Contacto: coordinacionpci@uas.edu.mx \n \n "+
                        "Página Web: https://pci.uas.edu.mx \n \n "+
                        "Dirección: Av. Josefa Ortiz de Domínguez S/N,Ciudad Universitaria. Culiacán Rosales, Sinaloa, México."))
                        .next()
                        .moveTo(estadoEspera);

                handleDireccion
                        .body(context -> twilioPlatform.reply(context, "Esta es nuestra dirección: \n \n "+
                        "Av. Josefa Ortiz de Domínguez S/N,Ciudad Universitaria. Culiacán Rosales, Sinaloa, México. "))
                        .next()
                        .moveTo(estadoEspera);
                
                handleModalidadExamenes
                        .body(context -> twilioPlatform.reply(context, "El proceso de entrevista al igual que los exámenes serán realizados en línea"))
                        .next()
                        .moveTo(estadoEspera);
                
                handleGuiasExamen
                        .body(context -> twilioPlatform.reply(context, "Estas son las guías para los exámenes que tengo disponibles por el momento \n \n "+
                        "Maestría: \n \n"+
                        "• Evaluación Conocimientos Básicos: https://pci.uas.edu.mx/wp-content/uploads/2022/03/Evaluacion_Conocimientos_Basicos.pdf \n \n "+
                        "• Evaluación Conocimientos en Ciencias de la Informática : https://pci.uas.edu.mx/wp-content/uploads/2022/03/Evaluacion-Conocimientos-en-Ciencias-de-la-Informacion.pdf \n \n"+
                        "Doctorado:\n\n"+
                        "examen general de conocimiento\n \n"+
                        "• Opción Terminal de Computación y Sistemas. https://pci.uas.edu.mx/wp-content/uploads/2022/03/opcion-terminal-de-computacion-y-sistemas.pdf \n \n"+
                        "• Opción terminal de Geodesia y Geomática. https://pci.uas.edu.mx/wp-content/uploads/2022/03/opcion-terminal-de-geomatica.pdf"))
                        .next()
                        .moveTo(estadoEspera);

                handleTienenBeca
                        .body(context -> twilioPlatform.reply(context, "Todos los estudiantes aceptados en cualquiera de los dos programas tienen derecho a solicitar beca CONACyT para cursar sus estudios, ya que estos programas se encuentran dentro de Padrón Nacional de Posgrados de Calidad del CONACyT.\n \n"+
                        "La beca dura 24 meses para Maestría y 48 meses para Doctorado."))
                        .next()
                        .moveTo(estadoEspera);
                
                handleRecepcionDocumentos
                        .body(context -> twilioPlatform.reply(context, "lo primero que tienes que hacer es realizar el siguiente proceso: \n \n"+
                        "1. Ingresar al sitio de internet dse.uasnet.mx/admision poner número de ficha y clave.\n \n"+
                        "2. Llenar la solicitud de preinscripción (con mayúsculas, sin acentos, sin comas. no abreviar, no omitir ningún nombre, ni apellido).\n \n"+
                        "3. Imprimir 2 veces la solicitud de preinscripción.\n \n"+
                        "4. Imprimir el recibo de pago.\n \n"+
                        "5. Pagar en banco Banorte o a banco Santander.\n \n"+
                        "6. A las 24 horas de haber hecho el pago, volver a entrar a la página dse.uasnet.mx/admision/ para llenar el formulario del ceneval. (Llenar en su totalidad y sin errores ortográficos).\n \n"+
                        "7. Imprimir dos veces el pase de ingreso al examen ceneval.\n \n"+
                        "8. Entregar la siguiente documentación para la preinscripción en Control Escolar de la Facultad de Informática Culiacán (FIC):\n \n"+
                        "   - Copia del acta de nacimiento.\n "+
                        "   - Copia del certificado de licenciatura o maestría (para doctorado).\n "+
                        "   - CURP impresa de internet.\n "+
                        "   - Solicitud de preinscripción.\n "+
                        "   - Recibo pagado.\n \n"+
                        "9. Al entregar la documentación se le tomará la foto y se le entregará la constancia de preinscripción.\n \n"+
                        "Despues de entregar la documentacion tendras que realizar el proceso de preinscripcion (puedes enviar un correo para preguntar si puedes enviar la documentacion por correo)" ))
                        .next()
                        .moveTo(estadoEspera);

                        
                entiendo
                        .body(context -> twilioPlatform.reply(context, "Entiendo, si necesitas algo más por favor házmelo saber"))
                        .next()
                        .moveTo(estadoEspera);

                handleOfrecerAyuda
                        .body(context -> twilioPlatform.reply(context, "Si tienes alguna otra duda por favor házmela saber, Te agradeceríamos que nos ayudaras para responder una breve encuesta y compartirnos cómo fue tu experiencia y así poder mejorar. Gracias😊\n \n"+
                        "Encuesta.com"))
                        .next()
                        .moveTo(estadoEspera);

                /*
         * The state that is executed if the engine doesn't find any navigable transition in a state and the state
         * doesn't contain a fallback.
         * <p>
         * The default fallback state is typically used to answer generic error messages, while state fallback can
         * benefit from contextual information to answer more precisely.
         * <p>
         * Note that every Xatkit bot needs a default fallback state.
         */
        val defaultFallback = fallbackState()
                .body(context -> twilioPlatform.reply(context, "Perdón, no lo entiendo"));

        /*
         * Creates the bot model that will be executed by the Xatkit engine.
         * <p>
         * A bot model contains:
         * - A list of platforms used by the bot. Xatkit will take care of starting and initializing the platforms
         * when starting the bot.
         * - A list of providers the bot should listen to for events/intents. As for the platforms Xatkit will take
         * care of initializing the provider when starting the bot.
         * - The entry point of the bot (a.k.a init state). The other states will be automatically collected by analyzing the state machine
         * - The default fallback state: the state that is executed if the engine doesn't find any navigable
         * transition in a state and the state doesn't contain a fallback.
         */

        
        val botModel = model()
                .usePlatform(twilioPlatform)
                .listenTo(twilioEventProvider)
                .initState(init)
                .defaultFallbackState(defaultFallback);

        Configuration botConfiguration = new BaseConfiguration();
        /*
         * Add configuration properties (e.g. authentication tokens, platform tuning, intent provider to use).
         * Check the corresponding platform's wiki page for further information on optional/mandatory parameters and
         * their values.
         */
       
       //dialogFlow
        
        botConfiguration.addProperty(IntentRecognitionProviderFactory.INTENT_PROVIDER_KEY, DialogFlowConfiguration.DIALOGLFOW_INTENT_PROVIDER);
        botConfiguration.addProperty(DialogFlowConfiguration.PROJECT_ID_KEY, "pruebaxatkit-sipv");
        botConfiguration.addProperty(DialogFlowConfiguration.GOOGLE_CREDENTIALS_PATH_KEY, "xDialog.json");
        botConfiguration.addProperty(DialogFlowConfiguration.LANGUAGE_CODE_KEY, "es");
        botConfiguration.addProperty(DialogFlowConfiguration.CLEAN_AGENT_ON_STARTUP_KEY, true);  
        
        botConfiguration.addProperty("xatkit.twilio.username", "AC8b7130bbbe59b296cddea45f8c8ca1f0");
        botConfiguration.addProperty("xatkit.twilio.auth.token", "0dd068b057dd6582c2c4394e797131c0");
        /*
        //NLP.js
        botConfiguration.addProperty(IntentRecognitionProviderFactory.INTENT_PROVIDER_KEY, NlpjsConfiguration.NLPJS_INTENT_PROVIDER);
        botConfiguration.addProperty(NlpjsConfiguration.AGENT_ID_KEY, "default");
        botConfiguration.addProperty(NlpjsConfiguration.LANGUAGE_CODE_KEY, "es");
        botConfiguration.addProperty(NlpjsConfiguration.NLPJS_SERVER_KEY, "http://localhost:8080"); 
        */

        XatkitBot xatkitBot = new XatkitBot(botModel, botConfiguration);
        xatkitBot.run();
        /*
         * The bot is now started, you can check http://localhost:5000/admin to test it.
         * The logs of the bot are stored in the logs folder at the root of this project.
         */
    }
}