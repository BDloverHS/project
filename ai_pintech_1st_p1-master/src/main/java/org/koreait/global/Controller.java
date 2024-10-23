package org.koreait.global;

import org.koreait.global.libs.Utils;
import org.koreait.main.controllers.MainController;

import java.util.function.Consumer;

/**
 * Controller 클래스
 *      모든 메뉴는 Controller 클래스를 상속 받아 정의 합니다.
 *      Controller 클래스는 사용자의 요청(예 - 메뉴 선택, 값 입력 등)이 유입되고
 *      이를 처리할 수 있는 서비스 객체와 화면 출력을 서로 연결해 주는 역할을 하게 됩니다.
 *      컨트롤러에서는 고정된 실행 절차가 정의되어 있고 이에 따라 실행 됩니다.
 *      이는 템플릿 메서드 패턴이 적용된 사례
 *      run() 메서드에는 실행 절차가 정의 되어 있습니다.
 *          실행 순서
 *          1) common() : 화면 상단 공통 출력 부분
 *          2) view() : 각 컨트롤러마다 다르게 출력될 부분 정의 - 상속 받은 클래스가 정의
 *          3) prompt() : 사용자의 입력을 받는 부분, 기본 화면은 메뉴 선택이지만 각 컨트롤러에서 새롭게 정의하면 해당 메뉴에 맞는 입력으로 새롭게 정의 가능
 */

// 추상 클래스. 모든 클래스는 얘의 하위 클래스
public abstract class Controller {


    private Object data; // 컨트롤러 전환시 전달할 데이터

    // 컨트롤러마다 사용자 입력은 다르게 처리되므로 열린 기능 형태로 함수형 인터페이스를 지정합니다.
    private Consumer<String> inputProcess;

    /**
     * 컨트롤러에 따라서 입력을 여러개 받는 경우, 예를 들면 상품 등록
     * 이때는 프롬프트 입력을 하나 등록하고 여기에 여러개 정의, 따로 없다면
     * 기본 한개의 입력을 받는다.
     */
    private Runnable promptProcess;

    // 공통 출력 부분
    public void common() {
        Utils.drawLine('-', 30);
        System.out.printf("상품 관리 프로그램 v1.0.0\n");
        Utils.drawLine('-', 30);
    }

    // 컨트롤러마다 다르게 정의되는 녀석들(하위 클래스가 구상해야함)
    public abstract void view(); // 화면 구성

    /**
     * 사용자 입력 문구
     *  - 화면에 따라서 입력 문구가 다른 경우 하기 메서드를 재정의 한다.
     *  - 그렇지 않다면 기본 문구 - 메뉴를 선택하세요(종료:Q): 출력 된다.
     * @return
     */

    // 문구가 바뀔 수 있음
    // protected는 상속과 관련함.
    protected String getPromptText() {
        return "메뉴를 선택하세요(종료:Q):";
    }

    /**
     * 컨트롤러 전환시 전달할 데이터
     *
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 컨트롤러 전환시 전달받은 데이터
     *
     * @return
     */
    public Object getData() {
        return data;
    }

    /**
     * 사용자 입력 처리
     *
     * @param inputProcess
     */
    protected void setInputProcess(Consumer<String> inputProcess) {
        this.inputProcess = inputProcess;
    }

    /**
     * 컨트롤러별 입력 새로 정의하는 경우 Runnable 인터페이스 구현체 추가
     *
     * @param promptProcess
     */
    protected void setPromptProcess(Runnable promptProcess) {
        this.promptProcess = promptProcess;
    }

    /**
     * 사용자 입력 데이터 처리
     * - 컨트롤러마다 처리는 다르므로 컨트롤러 마다 정의
     *
     */
    protected void process(String input) {
        // inputProcess -> 인터페이스
        if (inputProcess != null) {
            inputProcess.accept(input);
        }
    }

    /**
     * 사용자 입력 공통
     * - 사용자에게 입력을 받는 문구는 각 컨트롤러마다 달라질 수 있으므로
     * - getPromptText()를 하위 클래스에서 재정의하여 변경할 수 있습니다. 변경하지 않는다면 기본 메뉴 선택 문구로 출력이 됩니다.
     */

    public void prompt() {
        Utils.drawLine('-', 30);
        // 상황에 따라 다른 문자열이 나올 수 있도록 하는 함수
        System.out.print(getPromptText());

        if (promptProcess == null) {
            // 입력을 받음.
            String input = Router.sc.nextLine();

            // 입력 데이터 중 Q(대소문자 구분 없음)가 유입 되면 콘솔 프로그램 종료
            if (input.toUpperCase().equals("Q")) {
                System.out.println("종료합니다.");
                System.exit(0);
            } else if (input.toUpperCase().equals("M")) {
                // 입력 데이터가 M(대소문자 구분 없음)가 유입되면 메인 메뉴로 이동
                Utils.loadController(MainController.class);
            }

            /**
             *  입력에 대한 처리는 컨트롤러 마다 달라질 수 있으므로 값만 넘겨주고
             *  각 상속 받은 컨트롤러에서 등록한 Consumer<String> inputProcess 에서 처리한다.
             *  따라서 process는 inputProcess.accept(String input)에 사용자가 입력한 값만 넘겨주면서 호출해 준다.
             */
            process(input); // 입력 처리

        } else {
            // null이 아닐 경우 정의함
            promptProcess.run();
        }
    }

    /**
     * 콘솔 프로그램은 컨트롤러가 실행되는 절차가 고정되어 있으며 이는 변경이 불가 하다. 
     * 따라서 final로 하위 클래스가 재정의 하지 못하도록 고정합니다.
     * 실행 및 출력 순서는
     * 1) common() : 공통 화면
     * 2) view() : 하위클래스에서 구현한 view() 출력 
     * 3) prompt() : 사용자 입력 화면
     *
     * 이렇게 실행 절차를 고정해 놓은 함수를  템플릿 메서드라고 하며, 주로 추상 클래스에서 구현을 하므로 이를
     * 추상 템플릿 메서드 패턴이라고 합니다.
     */

    // 모든 컨트롤러는 run()을 호출
    // 추상 템플릿 메서드 패턴
    public final void run() throws Exception {
        // 순서를 바꾸지 못함.
        // final로 메서드 재정의 안 되도록 -> 모든 컨트롤러가 이 절차를 거치기 위함.
        common();
        view();
        prompt();
    }
}