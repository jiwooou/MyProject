package com.project.ex;

import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

@Controller
public class MemberController {
	@Autowired
	private MemDAO m_dao;
	
	@Autowired
	private HttpSession session; //�ڵ����� �����̵ȴ�.
	
	@RequestMapping("/login") //GET��� ȣ�� ��
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/login" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(String m_id, String m_pw){
		
		Map<String, Object> map = new Hashtable<String, Object>();
		
		// ���� �ΰ��� MemDAO�� login�Լ��� ȣ���ϸ鼭 �����ϸ� ��Ȯ�� ������ ���� MemVO�Ѱ��� �ް� �ȴ�.
		MemVO mvo = m_dao.login(m_id, m_pw);
		
		//mvo�� null�̸� ���̵� �� ��й�ȣ�� �� �� �Է��� ���! �׷��� ������ �� �Է��� ���
		if(mvo != null) {
				//���ǿ� mvo��� �̸����� mvo�� �����Ѵ�.
			session.setAttribute("mvo", mvo);
			map.put("res", "1");
			map.put("mvo", mvo);
		}else {
			map.put("res", "0");
		}
		return map;
	}
	
	@RequestMapping("logout")
	@ResponseBody
	public Map<String, String> logout() {
		// �������� ���� ����� mvo�� �����Ѵ�.
		session.removeAttribute("mvo");
		Map<String, String> map = new Hashtable<String, String>();
		map.put("res", "0");
		return map;
	}
}
